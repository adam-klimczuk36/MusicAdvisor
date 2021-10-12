package advisor.view;

import java.util.List;

public class MenuView {

    private boolean pageSet;
    private final int elementsPerPage;
    private int currentPageNo;
    private int allPagesNo;

    List<?> currentElements;

    public MenuView(int elemsPerPage) {
        this.elementsPerPage = elemsPerPage;
    }

    public void showMenu() {
        System.out.println();
        System.out.println("***********************");
        System.out.println("     Music advisor");
        System.out.println("***********************");
        System.out.println();
        System.out.println("Commands: ");
        System.out.println("auth - prints the auth link and allows user to use other commands");
        System.out.println("featured - a list of Spotify-featured playlists with their links fetched from API");
        System.out.println("new - a list of new albums with artists and links on Spotify");
        System.out.println("categories - a list of all available categories on Spotify (just their names)");
        System.out.println("playlists C_NAME - where C_NAME is the name of category. " +
                "The list contains playlists of this category and their links on Spotify");
        System.out.println("next - shows next page of a list if it's available");
        System.out.println("prev - shows previous page of a list if it's available");
        System.out.println("exit - shuts down the application");
        System.out.println();
    }

    public void showAuthLink(String ACCESS_SERVER) {
        System.out.println("use this link to request the access code:");
        System.out.println(ACCESS_SERVER + "/authorize" +
                "?client_id=936a9f2a7c0540a3b71f822c9c7e9dd3" +
                "&redirect_uri=http://localhost:8080" +
                "&response_type=code");
    }

    public void showAuthCodeWait() {
        System.out.println("waiting for code...");
    }

    public void showAuthCodeReceived() {
        System.out.println("code received");
    }

    public void showAccessToken(String token) {
        System.out.println("response:");
        System.out.println(token);
    }

    public void showAuthorizeSuccess() {
        System.out.println("---SUCCESS---");
    }

    public void showAuthorizeFailure() {
        System.out.println("---FAILURE---");
    }

    public void setCurrentElements(List<?> elements) {
        pageSet = true;
        currentElements = elements;
        currentPageNo = 1;
        allPagesNo = currentElements.size() / elementsPerPage;
        showElements();
    }

    public void showElements() {
        int from = (currentPageNo - 1) * elementsPerPage;
        int to = Math.min(currentPageNo * elementsPerPage, currentElements.size());
        currentElements.subList(from, to).forEach(System.out::println);
        System.out.printf("---PAGE %d OF %d---%n", currentPageNo, allPagesNo);
    }

    public void next() {
        if (currentPageNo < allPagesNo) {
            currentPageNo++;
            showElements();
        } else {
            System.out.println("No more pages.");
        }
    }

    public void prev() {
        if (currentPageNo > 1) {
            currentPageNo--;
            showElements();
        } else {
            System.out.println("No more pages.");
        }
    }

    public boolean isPageSet() {
        return pageSet;
    }

    public void showIOExceptionInfo() {
        System.out.println("Test unpredictable error message");
    }

    public void showNoAuthPage() {
        System.out.println("Please, provide access for application.");
    }

    public void showUserIsAuth() {
        System.out.println("User already authorized");
    }

    public void showUnknownCategoryName() {
        System.out.println("Unknown category name.");
    }

    public void showNoCommandPage() {
        System.out.println("Wrong command!");
    }

    public void showExitPage() {
        System.out.println("---GOODBYE!---");
    }
}
