package com.mailRecipients;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class ImapMailReader {
    private String host = "imap.gmail.com";
    private String port = "993";
    private String username;
    private String password;
    private Properties props;

    public ImapMailReader(String username, String password) {
        this.username = username;
        this.password = password;

        props = new Properties();
        props.put("mail.debug"          , "false"  );
        props.put("mail.store.protocol" , "imaps"  );
        props.put("mail.imap.ssl.enable", "true"   );
        props.put("mail.imap.port"      , port);
    }

    public void readEmail() {
        Authenticator auth = new EmailAuthenticator(username, password);

        Session session = Session.getInstance(props, auth);
        session.setDebug(false);

        try {
            Store store = session.getStore();

            // Подключение к почтовому серверу
            store.connect(host, username, password);

            // Папка входящих сообщений
            Folder inbox = store.getFolder("INBOX");

            // Открываем папку в режиме только для чтения
            inbox.open(Folder.READ_ONLY);

            System.out.println("Количество сообщений : " +
                    String.valueOf(inbox.getMessageCount()));
            if (inbox.getMessageCount() == 0)
                return;
            // Последнее сообщение; первое сообщение под номером 1
            Message message = inbox.getMessage(inbox.getMessageCount());
            Multipart mp = (Multipart) message.getContent();
            // Вывод содержимого в консоль
            for (int i = 0; i < mp.getCount(); i++){
                BodyPart  bp = mp.getBodyPart(i);
                if (bp.getFileName() == null)
                    System.out.println("    " + i + ". сообщение : '" +
                            bp.getContent() + "'");
                else
                    System.out.println("    " + i + ". файл : '" +
                            bp.getFileName() + "'");
            }
        } catch (NoSuchProviderException e) {
            System.err.println(e.getMessage());
        } catch (MessagingException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
