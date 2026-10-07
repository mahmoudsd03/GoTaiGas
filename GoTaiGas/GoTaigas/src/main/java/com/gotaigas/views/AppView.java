package com.gotaigas.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.*;
import com.gotaigas.control.AuthorizationControl;
import com.gotaigas.entities.User;
import com.gotaigas.util.Globals;
import com.gotaigas.util.Utils;

import com.vaadin.flow.component.button.Button;

import com.gotaigas.repository.StudentRepository;
import com.gotaigas.repository.UserRepository;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */

@CssImport("./styles/views/main/main-view.css")
@Route("main")
@JsModule("./styles/shared-styles.js")
public class AppView extends AppLayout implements BeforeEnterObserver, HasComponents {

    private Tabs menu;
    private H1 viewTitle;
    private H1 helloUser;

    private AuthorizationControl authorizationControl;
    private final StudentRepository studentRepository;
    private final UserRepository userRepo;


    public AppView(StudentRepository studentRepository, UserRepository userRepo) {
        this.studentRepository = studentRepository;
        this.userRepo = userRepo;


        if (getCurrentUser() == null) {
            System.out.println("LOG: In Constructor of App View - No User given!");
        } else {
            setUpUI();
        }
    }



    public void setUpUI() {
        // Anzeige des Toggles über den Drawer
        setPrimarySection(Section.DRAWER);

        // Erstellung der horizontalen Statusleiste (Header)
        addToNavbar(true, createHeaderContent());

        // Erstellung der vertikalen Navigationsleiste (Drawer)
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
    }


    private boolean checkIfUserIsLoggedIn() {
        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite gelenkt
        User userDTO = this.getCurrentUser();
        if (userDTO == null) {
            UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
            return false;
        }
        return true;
    }

    /**
     * Erzeugung der horizontalen Leiste (Header).
     * @return
     */
    private Component   createHeaderContent() {
        // Ein paar Grund-Einstellungen. Alles wird in ein horizontales Layout gesteckt.
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");

        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode( FlexComponent.JustifyContentMode.EVENLY );

        // Hinzufügen des Toogle ('Big Mac') zum Ein- und Ausschalten des Drawers
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        viewTitle.setWidthFull();
        layout.add( viewTitle );

        // Interner Layout
        HorizontalLayout topRightPanel = new HorizontalLayout();
        topRightPanel.setWidthFull();
        topRightPanel.setJustifyContentMode( FlexComponent.JustifyContentMode.END );
        topRightPanel.setAlignItems( FlexComponent.Alignment.CENTER );

        // Der Name des Users wird später reingesetzt, falls die Navigation stattfindet
        helloUser = new H1();
        helloUser.setId("helloUser_ID");
        topRightPanel.add(helloUser);

        topRightPanel.add(ThemeHelper.createThemeToggleButton());

        // Nutzer Avatar am rechts-oberen Rand.
        MenuBar bar = new MenuBar();
        bar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);
        Avatar userAvatar = new Avatar(this.getCurrentUser().getFirstName() +" "+this.getCurrentUser().getLastName());
        userAvatar.getStyle().set("display","block");
        userAvatar.getStyle().set("cursor","var(--vaadin-clickable-cursor)");

        MenuItem avatar = bar.addItem(userAvatar);
        SubMenu avatarMenu = avatar.getSubMenu();
        avatarMenu.addItem("Profil", e -> {
            UI.getCurrent().navigate(Globals.Pages.PROFILE);
        });
        avatarMenu.addItem("Logout", e -> logoutUser());

        topRightPanel.add(bar);

        layout.add( topRightPanel );
        return layout;
    }

    private Button createThemeToggleButton() {
        Button themeToggle = new Button("🌙");
        themeToggle.setId("theme-toggle");
        themeToggle.getElement().setAttribute("title", "Dark/Light Mode wechseln");

        themeToggle.addClickListener(event -> {
            boolean darkModeAktiv = UI.getCurrent()
                    .getElement()
                    .getThemeList()
                    .contains("dark");

            UI.getCurrent()
                    .getElement()
                    .getThemeList()
                    .set("dark", !darkModeAktiv);

            themeToggle.setText(darkModeAktiv ? "🌙" : "☀️");
        });

        return themeToggle;
    }

    private void logoutUser() {
        UI ui = this.getUI().get();
        ui.getSession().close();
        ui.getPage().setLocation("/");
    }

    /**
     * Hinzufügen der vertikalen Leiste (Drawer)
     * Diese besteht aus dem Logo ganz oben links sowie den Menu-Einträgen (menu items).
     * Die Menu Items sind zudem verlinkt zu den internen Tab-Components.
     * @param menu
     * @return
     */
    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);

        HorizontalLayout logoLayout = new HorizontalLayout();

        // Hinzufügen des Logos
        logoLayout.setId("logo");
        logoLayout.setWidthFull();
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        Image img = new Image("images/logoGoTaigas.png", "gotaigas logo");
        H1 header = new H1("Gotaigas");

        logoLayout.add(img);
        logoLayout.add(header);

        // Hinzufügen des Menus inklusive der Tabs
        layout.add(logoLayout, menu);
        return layout;
    }

    /**
     * Erzeugung des Menu auf der vertikalen Leiste (Drawer)
     * @return
     */
    private Tabs createMenu() {

        // Anlegen der Grundstruktur
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");

        // Anlegen der einzelnen Menuitems
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {
        authorizationControl = new AuthorizationControl();

        if (isCurrentUserStudent()) {
            return new Component[]{
                    createTab("Gründungsideen anzeigen", ShowIdeasView.class),
                    createTab("Gründungsidee erstellen", GruendungsideeView.class)
            };
        }

        return new Component[]{
                createTab("Gründungsideen anzeigen", ShowIdeasView.class)
        };
    }

       // Falls er Admin-Rechte hat, sollte der User auch Autos hinzufügen können
       // (Alternative: Verwendung der Methode 'isUserisAllowedToAccessThisFeature')


        /*
       if ( this.authorizationControl.isUserInRole( this.getCurrentUser() , Globals.Roles.ADMIN ) ) {
           System.out.println("User is Admin!");
           tabs = Utils.append( tabs , createTab("Enter Car", EnterCarView.class)  );
       }
*/
       // ToDo für die Teams: Weitere Tabs aus ihrem Projekt hier einfügen!

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();

        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite gelenkt
        if ( !checkIfUserIsLoggedIn() ) return;

        // Der aktuell-selektierte Tab wird gehighlighted.
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);

        // Setzen des aktuellen Names des Tabs
        viewTitle.setText(getCurrentPageTitle());

        // Setzen des Vornamens von dem aktuell eingeloggten Benutzer
        helloUser.setText("Willkommen "  + this.getCurrentNameOfUser() );

    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    private String getCurrentNameOfUser() {
        return Utils.getCurrentUser(userRepo).getFirstName();
    }

    private User getCurrentUser () {
        return Utils.getCurrentUser(userRepo);
    }



    @Override
    /**
     * Methode wird vor der eigentlichen Darstellung der UI-Components aufgerufen.
     * Hier kann man die finale Darstellung noch abbrechen, wenn z.B. der Nutzer nicht eingeloggt ist
     * Dann erfolgt hier ein ReDirect auf die Login-Seite. Eine Navigation (Methode navigate)
     * ist hier nicht möglich, da die finale Navigation noch nicht stattgefunden hat.
     * Diese Methode in der AppLayout sichert auch den un-authorisierten Zugriff auf die innerliegenden
     * Views (hier: ShowCarsView und EnterCarView) ab.
     *
     */
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (getCurrentUser() == null){
            beforeEnterEvent.rerouteTo(Globals.Pages.LOGIN_VIEW);
        }

    }
    private boolean isCurrentUserStudent() {
        User currentUser = getCurrentUser();

        return currentUser != null
                && studentRepository.findByUserId(currentUser.getId()).isPresent();
    }

}
