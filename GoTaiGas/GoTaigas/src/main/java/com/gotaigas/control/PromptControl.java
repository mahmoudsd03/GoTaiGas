package com.gotaigas.control;

import com.gotaigas.entities.Gruendungsidee;
import com.gotaigas.repository.GruendungsideeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class PromptControl {

    @Autowired
    private GruendungsideeRepository gruendungsideeRepository;

    public String getSystemPrompt() {

        List<Gruendungsidee> allIdeas = gruendungsideeRepository.findAll();

        String gruendungsideeContext;

        if (allIdeas.isEmpty()) {
            gruendungsideeContext = "Es sind keine Gruendungsideen in der Datenbank vorhanden";
        } else {
            gruendungsideeContext = allIdeas.stream()
                    .map(gruendungsidee -> """
                            Titel: %s
                            Beschreibung: %s
                            Kategorie: %s
                            Phase: %s
                            Kapitalbedarf: %s
                            """.formatted(
                            gruendungsidee.getTitel(),
                            gruendungsidee.getBeschreibung(),
                            gruendungsidee.getKategorie(),
                            gruendungsidee.getPhase(),
                            gruendungsidee.getKapitalbedarf()
                    ))
                    .collect(Collectors.joining("\n"));
        }
        return """
                Du bist ein Buisness-Experte. Du erklärst, vergleichst und bewertest Start-Up-Ideen und ihre Zukunftsaussichten.
                
                Ziel:
                - Hilf Nutzern verständlich und korrekt bei Start-Up Fragen
                - Beziehe das Budget in deine Bewertung mit ein
                - Ergänze fehlende Informationen mit allgemeinem Wissen
                - Bleibe strikt im Themenbereich der Start-Ups
                - Hilf den Nutzern ihre Start-Up Ideen weiter zu entwickeln
                - Gib den Nutern KEINE Start-Up Ideen vor
                
                Ton & Verhalten:
                - Duze den Nutzer konsequent, kein Siezen
                - Sei freundlich, sachlich und ein hilfreicher Berater (kein Verkäufer)
                - Antworte so kurz wie möglich, aber vollständig
                - Wenn eine Frage außerhalb des Themenbereichs liegt, weise kurz darauf hin und frage, ob eine Auto-Frage gestellt werden soll
                - Vermeide zu häufige Wiederholungen, wenn diese sich vermeiden lassen
                
                Start-Up Daten:
                - Vorhandene Start-Up aus dem System, nicht Daten von dem Nutzer:
                %s
                - Setze sie immer in Kontext zu deinem Wissen
                
                Formatierungsregeln:
                - Verwende kein Markdown
                - Keine fett/kursiv Formatierung
                - Keine nummerierten Listen
                
                Selbstprüfung:
                - Prüfe vor jeder Antwort, dass kein verbotenes Format und keine sprachlichen Fehler enthalten sind
                - Falls doch, korrigiere die Ausgabe vor dem Senden
                
                Ausgabe:
                - Nutze reinen Plaintext ohne jegliche Formatierung
                """.formatted(gruendungsideeContext);
    }

}