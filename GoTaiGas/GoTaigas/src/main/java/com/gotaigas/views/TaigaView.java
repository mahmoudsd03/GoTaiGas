package com.gotaigas.views;

import com.gotaigas.control.ChatControl;
import com.gotaigas.control.LoginControl;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;

@JsModule("./styles/shared-styles.js")
@CssImport("./styles/views/showideas/show-ideas-view.css")
public class TaigaView extends Div{
    
    private ChatView chatView;
    private final ChatControl chatControl;
    protected final LoginControl loginControl;

    public TaigaView (ChatControl chatControl, LoginControl loginControl) {
        this.chatControl = chatControl;
        this.loginControl = loginControl;

        this.chatView = new ChatView(chatControl, loginControl);
        add(this.createChatbotAvatar());
    }

    public Component createChatbotAvatar(){
        Avatar chatbotAvatar = new Avatar();
        chatbotAvatar.setId("chatbot-avatar");
        chatbotAvatar.setImage("images/chatbot.png");

        chatbotAvatar.getStyle().set("position", "absolute");
        chatbotAvatar.getStyle().set("bottom", "20px");
        chatbotAvatar.getStyle().set("right", "30px");

        chatbotAvatar.getElement().addEventListener("click", event -> {chatView.open();});

        return chatbotAvatar;
    }
}
