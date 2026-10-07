package com.gotaigas.views;

import java.util.List;
import java.util.Optional;

import com.gotaigas.control.ChatControl;
import com.gotaigas.control.GruendungsideeControl;
import com.gotaigas.control.LoginControl;
import com.gotaigas.control.RegistrationControl;
import com.gotaigas.control.RegistrationInvestorControl;
import com.gotaigas.control.RegistrationStudentControl;
import com.gotaigas.entities.Gruendungsidee;
import com.gotaigas.entities.Investor;
import com.gotaigas.entities.Student;
import com.gotaigas.entities.User;
import com.gotaigas.repository.InvestorRepository;
import com.gotaigas.repository.StudentRepository;
import com.gotaigas.repository.UserRepository;
import com.gotaigas.util.Globals;
import com.gotaigas.util.Utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.Scroller.ScrollDirection;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.dom.Style.Overflow;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;


@Route(value = Globals.Pages.PROFILE, layout = AppView.class)
public class ProfileView extends TaigaView{

    private final GruendungsideeControl ideaControl;
    private final StudentRepository studentRepo;
    private final InvestorRepository investorRepo;
    private final UserRepository userRepo;
    private final RegistrationStudentControl stuControl;
    private final RegistrationInvestorControl invControl;

    private boolean changeProfile = false;
    private Button delete;

    public ProfileView (GruendungsideeControl ideaControl,
            ChatControl chatControl,
            LoginControl loginControl,
            StudentRepository studentRepo,
            InvestorRepository investorRepo,
            UserRepository userRepo,
            RegistrationStudentControl stuControl,
            RegistrationInvestorControl invControl
        ) {
        super(chatControl, loginControl);

        addClassName("show-cars-view");

        this.ideaControl = ideaControl;
        this.studentRepo = studentRepo;
        this.investorRepo = investorRepo;
        this.userRepo = userRepo;
        this.stuControl = stuControl;
        this.invControl = invControl;

        this.setSizeFull();

        VerticalLayout layout = new VerticalLayout();

        // Vertical Layout styling options:
        layout.setSizeFull();
        layout.setPadding(true);
        layout.setAlignItems(Alignment.CENTER);


        layout.add (createProfileBox());
        if (getStudent(getCurrentUser()) != null)
            layout.add (createIdeaBox());

        this.add (new Scroller(layout));

    }

    private Component createProfileBox ()  {
        VerticalLayout profileBox = new VerticalLayout();
        profileBox.setAlignItems(Alignment.CENTER);
        profileBox.setHeightFull();
        profileBox.setMinWidth("300px");
        profileBox.setMaxWidth("1000px");
        profileBox.setPadding(true);

        H3 title = new H3("Profil");

        Icon icon = VaadinIcon.PENCIL.create();

        VerticalLayout box = new VerticalLayout();
        Div firstname = new Div();
        Div lastname = new Div();
        Div birthday = new Div();
        Div email = new Div();

        User currentUser = getCurrentUser();


        firstname.setText("Name: "+currentUser.getFirstName());
        lastname.setText("Nachname: "+currentUser.getLastName());
        birthday.setText("Geburtstag: "+currentUser.getDateOfBirth());
        email.setText("E-Mail: "+currentUser.getEmail());


        box.add(firstname, lastname, birthday, email);

        Student student = getStudent(currentUser);
        Investor investor = getInvestor (currentUser);

        if (student != null) {
            Div hochschule = new Div();
            Div studiengang = new Div();
            Div semester = new Div();

            hochschule.setText("Hochschule: "+student.getHochschule());
            studiengang.setText("Studiengang: "+student.getStudiengang());
            semester.setText("Semester: "+student.getSemester());

            box.add(hochschule,studiengang,semester);

        }else if (investor != null) {
            Div firma = new Div();
            Div focus = new Div();
            Div budget = new Div();

            firma.setText("Firma: "+investor.getFirma());
            focus.setText("Investment Fokus: "+investor.getInvestmentFokus());
            budget.setText("Budget: "+investor.getBudget());

            box.add(firma,focus,budget);
        }

        // Enabling change to profile
        icon.addClickListener(e -> {
            if (changeProfile)
                return;
            changeProfile = true;
            profileBox.remove(box);
            if (student != null)
                profileBox.add(createProfileChangeForm(stuControl,currentUser, student));
            else if (investor != null)
                profileBox.add(createProfileChangeForm(invControl, currentUser, investor));
        });


        HorizontalLayout header = new HorizontalLayout();
        header.setAlignItems(Alignment.CENTER);

        delete = new Button("User löschen");
        delete.addClickListener(e -> {
            if (student != null) {
                if (!ideaControl.readIdeasByStudent(currentUser).isEmpty()) {
                    Notification.show("User mit Gründungsideen können nicht gelöscht werden.");
                    return;
                }
                studentRepo.delete(student);
            } else if (investor != null)
                investorRepo.delete(investor);
            userRepo.delete(currentUser);
            logoutUser();
        });

        header.add(title,icon);

        box.add(delete);

        profileBox.add(header, box);



        return profileBox;
    }

    private void logoutUser() {
        UI ui = this.getUI().get();
        ui.getSession().close();
        ui.getPage().setLocation("/");
    }

    private Component createProfileChangeForm (RegistrationControl control, User user, Student student) {
        RegistrationStudentView changeForm = new RegistrationStudentView();
        changeForm.changeProfile(control,user,student);
        return changeForm;
    }
    private Component createProfileChangeForm (RegistrationControl control, User user, Investor investor) {
        RegistrationInvestorView changeForm = new RegistrationInvestorView();
        changeForm.changeProfile(control,user,investor);
        return changeForm;
    }

    private Investor getInvestor (User user) {
        if (user == null) return null;
        Optional<Investor> investor = investorRepo.findByUserId(user.getId());
        return !investor.isPresent() ? null : investor.get();
    }

 
    private Component createIdeaBox () {
        VerticalLayout ideaBox = new VerticalLayout();
        ideaBox.getStyle().setOverflow(Overflow.HIDDEN);
        //ideaBox.setMargin(true);
        ideaBox.setHeightFull();
        ideaBox.setMinWidth("300px");
        ideaBox.setMaxWidth("1000px");
        ideaBox.setPadding(true);
 
        H3 header = new H3("Meine Gruendungsideen");

        Component box = createCards(ideaControl);

        Scroller scroller = new Scroller(box, ScrollDirection.HORIZONTAL);

        ideaBox.add(header, scroller);
        
        return ideaBox;
    }

    private Component createCards(GruendungsideeControl ideaControl) {

        
        List <Gruendungsidee> ideaList = ideaControl.readIdeasByStudent(getCurrentUser());

        HorizontalLayout box = new HorizontalLayout();
        box.setPadding(true);
        box.setHeightFull();
        box.setMinWidth("300px");
        box.setMaxWidth("1000px");
        

        box.setPadding(true);
        

        for (Gruendungsidee idea : ideaList) {
            Card card = new Card(idea.getTitel());

            Div kategorie = new Div();
            Div phase = new Div();
            Div kapital = new Div();

            kategorie.setText("Kategorie: "+idea.getKategorie());
            phase.setText("Phase: "+idea.getPhase());
            kapital.setText("Kapitalbedarf: "+idea.getKapitalbedarf()+"€");


            TextArea beschreibung = new TextArea();
            beschreibung.setValue(idea.getBeschreibung());
            beschreibung.setMinHeight("100px");
            beschreibung.setMaxHeight("220px");
            beschreibung.setReadOnly(true);


            // Enabling deletion of Gruendungsideen
            Button deleteButton = new Button("Gruendungsidee löschen");

            deleteButton.addClickListener(event -> {
                User user = getCurrentUser();

                ideaControl.deleteIdea(idea,user);
                box.remove(card);

                Notification.show("Gründungsidee gelöscht.");
            });


            card.add(kategorie,phase,kapital,beschreibung,deleteButton);

            box.add(card);
        }

        return box;

    }

    private Student getStudent (User user) {
        if (user == null) return null;
        Optional<Student> student = studentRepo.findByUserId(user.getId());
        return !student.isPresent() ? null : student.get();
    }

    private User getCurrentUser () {
        return Utils.getCurrentUser(userRepo);
    }
   
    /*
     * Class was written by TatuLund
     * Original can be found via this link: https://gist.github.com/TatuLund/686b6868f97f5406fce089d468cb79e5
     * 
     * And modified by Hanna Budischewski
     */
public static class Card extends Composite<Div>
            implements HasComponents, HasSize {
        Div div = new Div();
        Div content = new Div();
        H4 title = new H4();
        String titleText;

        public Card(String titleText) {
            this.titleText = titleText;
        }

        public Card () {
            this("");
        }

        @Override
        public Div initContent() {
            title.setText(titleText);
            title.addClassNames(LumoUtility.Background.CONTRAST_5,
                    LumoUtility.TextColor.PRIMARY, LumoUtility.Padding.SMALL,
                    LumoUtility.Border.BOTTOM,
                    LumoUtility.BorderColor.CONTRAST_10);
            div.addClassNames(LumoUtility.Display.FLEX,
                    LumoUtility.FlexDirection.COLUMN, LumoUtility.Border.ALL,
                    LumoUtility.BorderColor.CONTRAST_10,
                    LumoUtility.BorderRadius.SMALL,
                    LumoUtility.BoxShadow.SMALL);
            content.addClassNames(LumoUtility.Flex.GROW,
                    LumoUtility.Padding.SMALL);
            div.add(title, content);
            return div;
        }

        public void setTitle (String title) {
            this.titleText = title;
            this.title.setText(titleText);
        }

        @Override
        public void add(Component... components) {
            content.add(components);
        }

        @Override
        public void remove(Component... components) {
            content.remove(components);
        }
    }
}
