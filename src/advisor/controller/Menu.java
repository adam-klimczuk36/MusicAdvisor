package advisor.controller;

import advisor.model.*;
import advisor.view.MenuView;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class Menu {
    boolean menu;
    boolean auth;
    UserDataManager userDataManager;
    UserData userData;
    AlbumManager albumManager;
    CategoryManager categoryManager;
    PlaylistFeaturedManager playlistFeaturedManager;
    PlaylistCategoryManager playlistCategoryManager;
    MenuView menuView;

    public Menu(String[] args) {
        setup(args);
    }

    private void setup(String[] args) {
        this.menu = true;
        this.auth = false;
        setupManagers(args);
        setupViews(args);
    }

    private void setupManagers(String[] args) {
        setupUserDataManager(args);
        this.userData = new UserData();
        this.albumManager = new AlbumManager();
        this.categoryManager = new CategoryManager();
        this.playlistFeaturedManager = new PlaylistFeaturedManager();
        this.playlistCategoryManager = new PlaylistCategoryManager();
    }

    private void setupViews(String[] args) {
        CommandLine cmd = Utils.getCliArguments(args);
        String elementsPerPage = Optional.ofNullable(cmd.getOptionValue("page"))
                .orElse("5");
        this.menuView = new MenuView(Integer.parseInt(elementsPerPage));
    }

    private void setupUserDataManager(String[] args) {
        CommandLine cmd = Utils.getCliArguments(args);
        final String ACCESS_SERVER = Optional.ofNullable(cmd.getOptionValue("access"))
                .orElse("https://accounts.spotify.com");
        final String API_SERVER = Optional.ofNullable(cmd.getOptionValue("resource"))
                .orElse("https://api.spotify.com");
        final String CLIENT_SECRET = Optional.ofNullable(cmd.getOptionValue("secret"))
                .orElse("empty");
        this.userDataManager = new UserDataManager(ACCESS_SERVER, API_SERVER, CLIENT_SECRET);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        menuView.showMenu();
        while(isMenu()) {
            String userInput = scanner.nextLine();
            switch (userInput.split(" ")[0]) {
                case "auth":
                    if(!userData.getUser().isLogged()) {
                        authorize();
                        break;
                    }
                    menuView.showUserIsAuth();
                    break;
                case "new":
                    if (isAuth()) {
                        showNew();
                    }
                    break;
                case "featured":
                    if (isAuth()) {
                        showFeatured();
                    }
                    break;
                case "categories":
                    if (isAuth()) {
                        showCategories();
                    }
                    break;
                case "playlists":
                    if (isAuth()) {
                        String C_NAME = userInput.split("playlists ")[1];
                        showPlaylists(C_NAME);
                    }
                    break;
                case "next":
                    if (isAuth() && menuView.isPageSet()) {
                        menuView.next();
                    }
                    break;
                case "prev":
                    if (isAuth() && menuView.isPageSet()) {
                        menuView.prev();
                    }
                    break;
                case "exit":
                    exit();
                    break;
                default:
                    menuView.showNoCommandPage();
                    break;
            }
        }
    }

    public void authorize() {
        menuView.showAuthLink(UserDataManager.ACCESS_SERVER);
        try {
            menuView.showAuthCodeWait();
            String userCode = userDataManager.getAuthCode();
            menuView.showAuthCodeReceived();
            userData.setUserAuthCode(userCode);
            userData.setCodes(userDataManager.requestAccessToken(userCode));
            menuView.showAccessToken(userData.getAccessToken());
            userData.getUser().setLogged();
            auth = true;
            menuView.showAuthorizeSuccess();
        } catch (IOException | InterruptedException e) {
            menuView.showAuthorizeFailure();
        }
    }

    public void showNew() {
        try {
            menuView.setCurrentElements(albumManager.getNewReleases(userDataManager, userData));
        } catch (IOException e) {
            menuView.showIOExceptionInfo();
        }
    }

    public void showFeatured() {
        try {
            menuView.setCurrentElements(playlistFeaturedManager.getFeaturedPlaylists(userDataManager, userData));
        } catch (IOException e) {
            menuView.showIOExceptionInfo();
        }
    }

    public void showCategories() {
        try {
            menuView.setCurrentElements(categoryManager.getCategories(userDataManager, userData));
        } catch (IOException e) {
            menuView.showIOExceptionInfo();
        }
    }

    public void showPlaylists(String C_NAME) {
        String categoryId = null;
        try {
            categoryId = categoryManager.getCategoryId(
                    categoryManager.getCategories(userDataManager, userData), C_NAME);
        } catch (IOException e) {
            menuView.showIOExceptionInfo();
        }

        if (categoryId != null) {
            try {
                menuView.setCurrentElements(playlistCategoryManager.getCategoryPlaylists(
                        userDataManager, userData, categoryId));

            } catch (IOException e) {
                menuView.showIOExceptionInfo();
            }
        } else {
            menuView.showUnknownCategoryName();
        }
    }

    public void exit() {
        menu = false;
        menuView.showExitPage();
    }

    public boolean isAuth() {
        if (!auth) {
            menuView.showNoAuthPage();
        }
        return auth;
    }

    public boolean isMenu() {
        return menu;
    }

}
