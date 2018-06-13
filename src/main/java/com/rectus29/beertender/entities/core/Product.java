package com.rectus29.beertender.entities.core;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 08/06/2016 19:59                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product extends GenericEntity {

    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String desc;

    private String imagePath;

    @ManyToMany(mappedBy = "productList")
    @JoinTable(name = "product_category")
    private List<Category> categoryList = new ArrayList<>();

    @Column(precision = 12, scale = 3)
    private BigDecimal price = BigDecimal.ZERO;

}
