/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Codename One through http://www.codenameone.com/ if you
 * need additional information or have any questions.
 */

package com.codename1.demos.ubereatsclone.controllers;

import com.codename1.demos.ubereatsclone.interfaces.MainWindow;
import com.codename1.demos.ubereatsclone.models.AccountModel;
import com.codename1.demos.ubereatsclone.views.HomeView;
import com.codename1.demos.ubereatsclone.views.MainWindowView;
import com.codename1.demos.ubereatsclone.views.ProfileView;
import com.codename1.demos.ubereatsclone.views.RestaurantView;
import com.codename1.rad.controllers.Controller;
import com.codename1.rad.controllers.FormController;
import com.codename1.rad.models.Entity;
import com.codename1.rad.nodes.ActionNode;
import com.codename1.rad.nodes.Node;
import com.codename1.rad.nodes.ViewNode;
import com.codename1.rad.ui.UI;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;

public class MainWindowController extends FormController {

    MainWindowView mainWindowView;

    public static final ActionNode editProfile = UI.action();
    public static final ActionNode editAddresses = UI.action();
    public static final ActionNode editCreditCards = UI.action();
    public static final ActionNode logOut = UI.action();
    public static final ActionNode updateProfileView = UI.action();

    public static final ActionNode enterRest = UI.action();
    public static final ActionNode recommendedExplore = UI.action();
    public static final ActionNode popularExplore = UI.action();

    public static final ActionNode enterFilter = UI.action();
    public static final ActionNode enterSearch = UI.action();

    public static final ActionNode addFavorite = UI.action();
    public static final ActionNode removeFavorite = UI.action();

    public MainWindowController(Controller parent, Entity appEntity) {
        super(parent);
        Form mainWindowForm = new Form(new BorderLayout());
        mainWindowForm.getToolbar().hideToolbar();
        Node profileNode = createProfileNode();
        Node homeNode = createHomeNode();

        mainWindowView = new MainWindowView(appEntity, profileNode, homeNode);
        mainWindowForm.add(BorderLayout.CENTER, mainWindowView);

        setView(mainWindowForm);

        addActionListener(editProfile, evt -> {
            evt.consume();
            Entity accountEntity = evt.getEntity();
            new EditProfileController(this, accountEntity, profileNode).getView().show();
        });

        addActionListener(editAddresses, evt -> {
            evt.consume();
            AccountModel accountEntity = (AccountModel)evt.getEntity();
            new EditAddressesController(this, accountEntity).getView().show();
        });

        addActionListener(editCreditCards, evt -> {
            evt.consume();
            AccountModel account = (AccountModel)evt.getEntity();
            new EditCreditCardsController(this, account).getView().show();

        });

        addActionListener(enterRest, evt -> {
            evt.consume();
            Entity restEntity = evt.getEntity();
            new RestaurantController(this, restEntity, appEntity.getEntity(MainWindow.profile), homeNode).getView().show();
        });

        addActionListener(enterFilter, evt -> {
            evt.consume();
            new FilterController(this, appEntity.getEntity(MainWindow.filter), homeNode).getView().show();
        });

        addActionListener(enterSearch, evt -> {
            evt.consume();
            new SearchController(this, appEntity, homeNode).getView().show();
        });

        addActionListener(addFavorite, evt -> {
            evt.consume();
            if(appEntity.get(MainWindow.profile) instanceof AccountModel){
                ((AccountModel) appEntity.get(MainWindow.profile)).addFavorite(evt.getEntity());
            }
            mainWindowView.addFavorite(evt.getEntity());
        });

        addActionListener(removeFavorite, evt -> {
            evt.consume();
            if(appEntity.get(MainWindow.profile) instanceof AccountModel){
                ((AccountModel) appEntity.get(MainWindow.profile)).removeFavorite(evt.getEntity());
            }
            mainWindowView.removeFavorite(evt.getEntity());
        });
    }

    private Node createProfileNode(){
        return new ViewNode(
                UI.actions(ProfileView.EDIT_PROFILE, editProfile),
                UI.actions(ProfileView.EDIT_ADDRESSES, editAddresses),
                UI.actions(ProfileView.EDIT_CREDIT_CARDS, editCreditCards),
                UI.actions(ProfileView.UPDATE_VIEW, logOut),
                UI.actions(ProfileView.LOG_OUT, updateProfileView)
        );
    }

    private Node createHomeNode(){
        return new ViewNode(
                UI.actions(HomeView.ENTER_REST, enterRest),
                UI.actions(HomeView.RECOMMENDED_EXPLORE, recommendedExplore),
                UI.actions(HomeView.POPULAR_EXPLORE, popularExplore),
                UI.actions(HomeView.ENTER_FILTER, enterFilter),
                UI.actions(HomeView.ENTER_SEARCH, enterSearch),
                UI.actions(RestaurantView.ADD_TO_FAVORITE, addFavorite),
                UI.actions(RestaurantView.REMOVE_FAVORITE, removeFavorite)
        );
    }


}