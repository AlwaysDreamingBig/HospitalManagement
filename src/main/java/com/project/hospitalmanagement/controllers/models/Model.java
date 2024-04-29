package com.project.hospitalmanagement.controllers.models;


import com.project.hospitalmanagement.controllers.factories.PageFactory;
import com.project.hospitalmanagement.controllers.factories.adminpagefactory.AdminPageFactory;

public class Model {
    private static Model model;

    private final PageFactory pageFactory;
    private final AdminPageFactory adminPageFactory;


    private Model(){

        this.pageFactory = new PageFactory();
        this.adminPageFactory = new AdminPageFactory();
    }

    public static synchronized Model getInstance(){

        if(model == null){

            model = new Model();
        }

        return model;
    }

    public PageFactory getpageFactory(){

        return pageFactory;
    }

    public AdminPageFactory getAdminPageFactory(){

        return adminPageFactory;
    }
}
