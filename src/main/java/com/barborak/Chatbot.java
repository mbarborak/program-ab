package com.barborak;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.Graphmaster;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

@Path("chatbot")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class Chatbot {
	static final Chat chatSession;

	static {
        MagicStrings.root_path = "./src/main/resources";
        AIMLProcessor.extension =  new PCAIMLProcessorExtension();
        String botName = "sassubot";
        Graphmaster.enableShortCuts = true;
        Bot bot = new Bot(botName, MagicStrings.root_path, "chat");
        chatSession = new Chat(bot);
	}
	
	@POST
	public String postRequest(String req) {
		synchronized (chatSession) {
			return chatSession.multisentenceRespond(req);
		}
	}
}
