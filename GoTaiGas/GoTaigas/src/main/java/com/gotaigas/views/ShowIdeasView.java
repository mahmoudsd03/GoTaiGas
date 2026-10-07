package com.gotaigas.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import com.gotaigas.control.GruendungsideeControl;
import com.gotaigas.entities.Gruendungsidee;
import com.gotaigas.entities.User;
import com.gotaigas.util.Globals;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;


import com.gotaigas.control.ChatControl;
import com.gotaigas.control.LoginControl;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


import java.math.BigDecimal;
import java.util.List;

@Route(value = Globals.Pages.SHOW_IDEAS, layout = AppView.class)
@PageTitle("Gründungsideen")
public class ShowIdeasView extends TaigaView {

    private  List<Gruendungsidee> ideaList;

    private final GruendungsideeControl ideaControl;

    public ShowIdeasView(GruendungsideeControl ideaControl , ChatControl  chatControl, LoginControl loginControl) {
        super(chatControl, loginControl);
        addClassName("show-ideas-view");

        this.ideaControl = ideaControl;

        ideaList = ideaControl.readAllIdeas();

        add(createTitle());
        add(createGridTable(ideaControl));
    }

    private Component createGridTable(GruendungsideeControl ideaControl) {
        Grid<Gruendungsidee> grid = new Grid<>();

        ListDataProvider<Gruendungsidee> dataProvider = new ListDataProvider<>(ideaList);
        grid.setDataProvider(dataProvider);

        Grid.Column<Gruendungsidee> titelColumn =
                grid.addColumn(Gruendungsidee::getTitel).setHeader("Titel");

        Grid.Column<Gruendungsidee> kategorieColumn =
                grid.addColumn(Gruendungsidee::getKategorie).setHeader("Kategorie");

        Grid.Column<Gruendungsidee> phaseColumn =
                grid.addColumn(Gruendungsidee::getPhase).setHeader("Phase");

        Grid.Column<Gruendungsidee> kapitalColumn =
                grid.addColumn(Gruendungsidee::getKapitalbedarf).setHeader("Kapitalbedarf");

        grid.addColumn(Gruendungsidee::getBeschreibung).setHeader("Beschreibung");

        HeaderRow filterRow = grid.appendHeaderRow();

        TextField titelField = new TextField();
        TextField kategorieField = new TextField();
        TextField phaseField = new TextField();
        BigDecimalField minBudgetField = new BigDecimalField();
        BigDecimalField maxBudgetField = new BigDecimalField();

        Runnable applyFilters = () -> {
            BigDecimal minBudget = minBudgetField.getValue();
            BigDecimal maxBudget = maxBudgetField.getValue();
            boolean invalidRange = minBudget != null && maxBudget != null
                    && minBudget.compareTo(maxBudget) > 0;

            minBudgetField.setInvalid(invalidRange);
            maxBudgetField.setInvalid(invalidRange);

            dataProvider.setFilter(idea -> {
                BigDecimal budget = idea.getKapitalbedarf();
                return !invalidRange
                        && (StringUtils.isBlank(titelField.getValue())
                        || StringUtils.containsIgnoreCase(idea.getTitel(), titelField.getValue()))
                        && (StringUtils.isBlank(kategorieField.getValue())
                        || StringUtils.containsIgnoreCase(idea.getKategorie(), kategorieField.getValue()))
                        && (StringUtils.isBlank(phaseField.getValue())
                        || StringUtils.containsIgnoreCase(idea.getPhase(), phaseField.getValue()))
                        && (minBudget == null || budget != null && budget.compareTo(minBudget) >= 0)
                        && (maxBudget == null || budget != null && budget.compareTo(maxBudget) <= 0);
            });
        };

        titelField.addValueChangeListener(event -> applyFilters.run());
        kategorieField.addValueChangeListener(event -> applyFilters.run());
        phaseField.addValueChangeListener(event -> applyFilters.run());
        minBudgetField.addValueChangeListener(event -> applyFilters.run());
        maxBudgetField.addValueChangeListener(event -> applyFilters.run());

        //die Buttons müssten erstellt werden :

        grid.addComponentColumn(idee -> {
            Button detailsButton = new Button("Details");
            detailsButton.addClickListener(event -> openIdeaDetailsDialog(idee));

            HorizontalLayout actions = new HorizontalLayout();
            actions.setSpacing(true);
            actions.setPadding(false);
            actions.setMargin(false);

            actions.add(detailsButton);

            User currentUser = getCurrentUser();

            if (isCreatorOfIdea(idee, currentUser)) {
                Button deleteButton = new Button("Löschen");

                deleteButton.addClickListener(event -> {
                    User user = getCurrentUser();

                    if (!isCreatorOfIdea(idee, user)) {
                        Notification.show("Du darfst nur deine eigenen Gründungsideen löschen.");
                        return;
                    }

                    ideaControl.deleteIdea(idee,user);
                    ideaList.remove(idee);
                    dataProvider.refreshAll();

                    Notification.show("Gründungsidee gelöscht.");
                });

                actions.add(deleteButton);
            }

            return actions;
        }).setHeader("Aktion").setAutoWidth(true).setFlexGrow(0);


        titelField.setValueChangeMode(ValueChangeMode.EAGER);
        titelField.setSizeFull();
        titelField.setPlaceholder("Titel suchen");
        filterRow.getCell(titelColumn).setComponent(titelField);

        kategorieField.setValueChangeMode(ValueChangeMode.EAGER);
        kategorieField.setSizeFull();
        kategorieField.setPlaceholder("Kategorie suchen");
        filterRow.getCell(kategorieColumn).setComponent(kategorieField);

        phaseField.setValueChangeMode(ValueChangeMode.EAGER);
        phaseField.setSizeFull();
        phaseField.setPlaceholder("Phase suchen");
        filterRow.getCell(phaseColumn).setComponent(phaseField);

        minBudgetField.setPlaceholder("Von");
        maxBudgetField.setPlaceholder("Bis");
        minBudgetField.setWidth("50%");
        maxBudgetField.setWidth("50%");
        HorizontalLayout budgetFilter = new HorizontalLayout(minBudgetField, maxBudgetField);
        budgetFilter.setPadding(false);
        budgetFilter.setSpacing(true);
        budgetFilter.setWidthFull();
        filterRow.getCell(kapitalColumn).setComponent(budgetFilter);

        return grid;
    }

    private Component createTitle() {
        return new H3("Gründungsideen");
    }

    //ab hier 2 Methoden für die "Details" in ShowView
    private void openIdeaDetailsDialog(Gruendungsidee idee) {
        Dialog dialog = new Dialog();
        dialog.getElement().getThemeList().add("idea-details-dialog");

        dialog.setHeaderTitle("Details zur Gründungsidee");

        VerticalLayout content = new VerticalLayout();
        content.setPadding(false);
        content.setSpacing(true);
        content.setWidth("520px");
        content.setMaxWidth("90vw");

        content.add(createDetailRow("Titel", idee.getTitel()));
        content.add(createDetailRow("Kategorie", idee.getKategorie()));
        content.add(createDetailRow("Phase", idee.getPhase()));
        content.add(createDetailRow("Kapitalbedarf", idee.getKapitalbedarf()));

        Span beschreibungLabel = new Span("Beschreibung:");
        beschreibungLabel.getStyle().set("font-weight", "bold");

        Span beschreibungText = new Span(valueOrDash(idee.getBeschreibung()));
        beschreibungText.getStyle()
                .set("white-space", "pre-wrap")
                .set("line-height", "1.5");

        content.add(beschreibungLabel, beschreibungText);

        if (idee.getStudent() != null) {
            content.add(createDetailRow("Hochschule", idee.getStudent().getHochschule()));
            content.add(createDetailRow("Studiengang", idee.getStudent().getStudiengang()));
            content.add(createDetailRow("Semester", idee.getStudent().getSemester()));

            if (idee.getStudent().getUser() != null) {
                String name = idee.getStudent().getUser().getFirstName()
                        + " "
                        + idee.getStudent().getUser().getLastName();

                content.add(createDetailRow("Erstellt von", name));
                content.add(createDetailRow("E-Mail", idee.getStudent().getUser().getEmail()));
            }
        }

        Button closeButton = new Button("Schließen", event -> dialog.close());

        dialog.add(content);
        dialog.getFooter().add(closeButton);
        dialog.open();
    }

    private Component createDetailRow(String label, Object value) {
        HorizontalLayout row = new HorizontalLayout();
        row.setWidthFull();
        row.setSpacing(true);

        Span labelSpan = new Span(label + ":");
        labelSpan.getStyle()
                .set("font-weight", "bold")
                .set("min-width", "130px");

        Span valueSpan = new Span(valueOrDash(value));
        valueSpan.getStyle().set("white-space", "normal");

        row.add(labelSpan, valueSpan);
        return row;
    }

    private String valueOrDash(Object value) {
        return value == null || value.toString().isBlank()
                ? "-"
                : value.toString();
    }


    private User getCurrentUser() {
        return loginControl.getCurrentUser();
    }

    private boolean isCreatorOfIdea(Gruendungsidee idee, User currentUser) {
        return currentUser != null
                && idee != null
                && idee.getStudent() != null
                && idee.getStudent().getUser() != null
                && idee.getStudent().getUser().getId() == currentUser.getId();
    }

}
