package com.gotaigas.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;

public class ThemeHelper {

    public static void applySavedTheme() {
        UI.getCurrent().getPage().executeJs("""
            const savedTheme = localStorage.getItem('app-theme');

            if (savedTheme === 'dark') {
                document.documentElement.setAttribute('theme', 'dark');
            } else {
                document.documentElement.removeAttribute('theme');
            }
        """);
    }

    public static Button createThemeToggleButton() {
        Button themeToggle = new Button("🌙");
        themeToggle.setId("theme-toggle");
        themeToggle.getElement().setAttribute("title", "Dark/Light Mode wechseln");

        themeToggle.addAttachListener(event -> {
            event.getUI().getPage().executeJs("""
                const savedTheme = localStorage.getItem('app-theme');

                if (savedTheme === 'dark') {
                    document.documentElement.setAttribute('theme', 'dark');
                    $0.textContent = '☀️';
                } else {
                    document.documentElement.removeAttribute('theme');
                    $0.textContent = '🌙';
                }
            """, themeToggle.getElement());
        });

        themeToggle.addClickListener(event -> {
            UI.getCurrent().getPage().executeJs("""
                const html = document.documentElement;
                const isDark = html.getAttribute('theme') === 'dark';

                if (isDark) {
                    html.removeAttribute('theme');
                    localStorage.setItem('app-theme', 'light');
                    $0.textContent = '🌙';
                } else {
                    html.setAttribute('theme', 'dark');
                    localStorage.setItem('app-theme', 'dark');
                    $0.textContent = '☀️';
                }
            """, themeToggle.getElement());
        });

        return themeToggle;
    }
}