# Beertender

an application to manage beer buying, all java for the moment, the front may be replace by an REACT rich client application


# Workflow
### Order workflow
 - ENABLE by default (the order is editable by customer and in an active timeframe)
 - when the timeFrame come to his ends the order is place to PENDING when the timeframe is LOCKED (customer cannot edit the order anymore) 
 - when the order is validate by admin the order pass to LOCKED, (customer and admin cannot edit the order anymore, but admin can unlock in case of ...) 
 - when the timeFrame is set to DISABLED meaning all work is done by admin, the order beacome DISABLED too, note a TIMEFRAME cannot be DISABLED if a order is PENDING  

### TimeFrame Workflow
 - PENDING by default until activation
 - ENABLE while in the defined timeFrame
 - LOCKED at the end of the defined timeFrame
 - DISABLED if all the order are LOCKED 



# Link Wall 

ref link to h2 stub
http://www.h2database.com/html/cheatSheet.html


hibernate search reference
https://docs.jboss.org/hibernate/stable/search/reference/en-US/html_single/#example-configuring-directory-providers

https://templatemag.com/demo/Dashio/


Spring configuration by annotation 
http://javaetmoi.com/2014/06/spring-framework-java-configuration/
