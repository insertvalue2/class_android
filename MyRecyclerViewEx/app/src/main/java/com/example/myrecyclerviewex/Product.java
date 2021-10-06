package com.example.myrecyclerviewex;

import java.util.ArrayList;

public class Product {

    String productName;
    String location;
    String date;
    String price;
    int likeCount;
    int commentCount;
    String imageUri;

    public Product(String productName, String location, String date, String price, int likeCount, int commentCount, String imageUri) {
        this.productName = productName;
        this.location = location;
        this.date = date;
        this.price = price;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.imageUri = imageUri;
    }


    // 샘플 데이터 생성
    // image : https://github.com/flutter-coder/ui_images
    public static ArrayList<Product> getProductList() {
        ArrayList<Product> productList = new ArrayList();
        productList.add(
                new Product(
                        "니트 조끼",
                        "좌동",
                        "2시간전",
                        "35,000원",
                        8,
                        3,
                        "https://github.com/flutter-coder/ui_images/blob/master/carrot_product_1.jpg?raw=true"
                ));
        productList.add(
                new Product(
                        "먼나라 이웃나라",
                        "좌동",
                        "3시간전",
                        "18,000원",
                        8,
                        3,
                        "https://github.com/flutter-coder/ui_images/blob/master/carrot_product_2.jpg?raw=true"
                ));

        productList.add(
                new Product(
                        "유럽 여행",
                        "우동",
                        "3일전",
                        "18,000원",
                        8,
                        3,
                        "https://github.com/flutter-coder/ui_images/blob/master/carrot_product_3.jpg?raw=true"
                ));

        productList.add(
                new Product(
                        "캐나다구스 패딩조끼",
                        "좌동",
                        "2시간전",
                        "35,000원",
                        8,
                        31,
                        "https://github.com/flutter-coder/ui_images/blob/master/carrot_product_4.jpg?raw=true"
                ));

        productList.add(
                new Product(
                        "가죽 파우치",
                        "좌동",
                        "2시간전",
                        "35,000원",
                        8,
                        3,
                        "https://github.com/flutter-coder/ui_images/blob/master/carrot_product_5.jpg?raw=true"
                ));

        productList.add(
                new Product(
                        "노트북",
                        "좌동",
                        "2시간전",
                        "1,135,000원",
                        21,
                        31,
                        "https://github.com/flutter-coder/ui_images/blob/master/carrot_product_6.jpg?raw=true"
                ));

        productList.add(
                new Product(
                        "데스크탑",
                        "좌동",
                        "5일전",
                        "1,335,000원",
                        18,
                        23,
                        "https://github.com/flutter-coder/ui_images/blob/master/carrot_product_7.jpg?raw=true"
                ));


        return productList;
    }
}
