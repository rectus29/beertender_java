package com.rectus29.beertender.web.panel.newsPanel;

import com.rectus29.beertender.entities.core.Config;
import com.rectus29.beertender.entities.core.News;
import com.rectus29.beertender.enums.NewsType;
import com.rectus29.beertender.service.IserviceConfig;
import com.rectus29.beertender.service.IserviceNews;
import com.rectus29.beertender.web.component.andilmodal.EveModal;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/*-----------------------------------------------------*/
/* User: Rectus_29      Date: 27/01/15 18:28 			*/
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public class NewsPanel extends Panel {
    private static final Logger log = LogManager.getLogger(NewsPanel.class);
    @SpringBean(name = "serviceNews")
    private IserviceNews serviceNews;
    @SpringBean(name = "serviceConfig")
    private IserviceConfig serviceConfig;
    private LoadableDetachableModel<List<DisplayableNews>> ldm;
    private EveModal modal;
    private int nbNews = 5;
    private int displayNews = 0;
    private String imgThumb;

    public NewsPanel(String id) {
        super(id);
    }

    public NewsPanel(String id, int nbNews) {
        super(id);
        this.nbNews = nbNews;

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        ldm = new LoadableDetachableModel<List<DisplayableNews>>() {
            @Override
            protected List<DisplayableNews> load() {
                List<DisplayableNews> out = new ArrayList<DisplayableNews>();
                for (News temp : serviceNews.getEnableAndPublishNews(getNbNews())) {
                    out.add(new DisplayableNews(temp, "/news/" + temp.getId()));
                }
                Config conf = serviceConfig.getByKey("rssfeed");
                if (conf != null && conf.getValue() != null) {
                    for (String temp : conf.getValue().split(";")) {
                        try {
                            MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
                            HttpConnectionManagerParams params = new HttpConnectionManagerParams();
                            params.setConnectionTimeout(15000);
                            params.setSoTimeout(15000);
                            connectionManager.setParams(params);
                            HttpClient httpclient = new HttpClient(connectionManager);
                            HttpClientParams clientParams = new HttpClientParams();
                            clientParams.setParameter("http.protocol.allow-circular-redirects", true);
                            clientParams.setParameter("http.protocol.max-redirects", 4);
                            httpclient.setParams(clientParams);
                            GetMethod get = new GetMethod(temp);
                            httpclient.executeMethod(get);
                            SyndFeedInput input = new SyndFeedInput();
                            SyndFeed feed = input.build(new XmlReader(get.getResponseBodyAsStream()));
                            Iterator i = feed.getEntries().iterator();
                            while (i.hasNext()) {
                                SyndEntry entry = (SyndEntry) i.next();
                                News rssNews = new News();
                                rssNews.setTitle(entry.getTitle());
                                rssNews.setPublishDate((entry.getPublishedDate() != null) ? entry.getPublishedDate() : new Date());
                                rssNews.setShortText(entry.getDescription().getValue());
                                if (entry.getContents().size() > 0)
                                    rssNews.setText(entry.getContents().get(0).toString());
                                DisplayableNews dn = new DisplayableNews(rssNews, entry.getLink());
                                dn.setNewsType(NewsType.EXTERNAL);
                                out.add(dn);
                                displayNews++;
                            }
                        } catch (Exception e) {
                            log.error("Error while Rss smf parsing", e);
                        }
                    }
                }
                Collections.sort(out, new Comparator<DisplayableNews>() {
					public int compare(DisplayableNews o1, DisplayableNews o2) {
						return o2.getNews().getPublishDate().compareTo(o1.getNews().getPublishDate());
					}
				});
                out = out.subList(0, ((out.size() > getNbNews()) ? getNbNews() : out.size()));
                return out;
            }
        };

        ListView<DisplayableNews> newsListView = new ListView<DisplayableNews>("news", ldm) {
            @Override
            protected void populateItem(final ListItem<DisplayableNews> newsListItem) {
                final DisplayableNews dNews = newsListItem.getModelObject();
                newsListItem.add(new Label("date", new SimpleDateFormat("dd/MM/yyyy").format(newsListItem.getModelObject().getNews().getPublishDate())));
                newsListItem.add(new Label("shortText", newsListItem.getModelObject().getNews().getShortText()).setEscapeModelStrings(false));
                imgThumb = null;
                 /*Matcher m = Pattern.compile("<img(.*?)>").matcher(newsListItem.getModelObject().getNews().getText());
                 if(m.find()){
                     imgThumb = m.group(0);
                 }       */
                newsListItem.add(new Label("thumb", imgThumb) {
                    @Override
                    public boolean isVisible() {
                        return imgThumb != null && imgThumb.length() > 0 && false;
                    }
                }.setEscapeModelStrings(false));
                if (newsListItem.getModelObject().getNewsType().equals(NewsType.EXTERNAL)) {
                    newsListItem.add(new ExternalLink("titleLink", newsListItem.getModelObject().getBackLink()).add(new Label("title", newsListItem.getModelObject().getNews().getTitle())));
                } else if (newsListItem.getModelObject().getNewsType().equals(NewsType.INTERNAL)) {
                    newsListItem.add(new AjaxLink("titleLink") {
                        @Override
                        public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                            modal.setTitle(dNews.getNews().getTitle());
                            modal.setContent(new Label(modal.getContentId(), "<div class='news-content'>" + dNews.getNews().getText() + "</div>").setEscapeModelStrings(false));
                            modal.show(ajaxRequestTarget);
                        }
                    }.add(new Label("title", newsListItem.getModelObject().getNews().getTitle())));
                }
                newsListItem.add(new AttributeAppender("class", newsListItem.getModelObject().getNewsType().toString()));
            }
        };
        add(newsListView.setOutputMarkupId(true));
        add((modal = new EveModal("modal")).setOutputMarkupId(true));
    }

    private class DisplayableNews implements Serializable {
        private News news;
        private String backLink;
        private NewsType newsType = NewsType.INTERNAL;

        private DisplayableNews(News news, String backLink) {
            this.news = news;
            this.backLink = backLink;
        }

        private News getNews() {
            return news;
        }

        private void setNews(News news) {
            this.news = news;
        }

        private String getBackLink() {
            return backLink;
        }

        private void setBackLink(String backLink) {
            this.backLink = backLink;
        }

        private NewsType getNewsType() {
            return newsType;
        }

        private void setNewsType(NewsType newsType) {
            this.newsType = newsType;
        }
    }

    public int getNbNews() {
        return nbNews;
    }
}
