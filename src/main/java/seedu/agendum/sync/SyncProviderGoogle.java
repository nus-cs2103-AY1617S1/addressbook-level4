package seedu.agendum.sync;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.Calendar;
import seedu.agendum.commons.core.LogsCenter;
import seedu.agendum.model.task.Task;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static java.lang.Math.abs;
import static seedu.agendum.commons.core.Config.DEFAULT_DATA_DIR;

//@@author A0003878Y
public class SyncProviderGoogle extends SyncProvider {
    private final Logger logger = LogsCenter.getLogger(SyncProviderGoogle.class);

    private static final String CALENDAR_NAME = "Agendum Calendar";
    private static final File DATA_STORE_DIR = new File(DEFAULT_DATA_DIR);
    private static final File DATA_STORE_CREDENTIAL = new File(DEFAULT_DATA_DIR + "StoredCredential");
    private static final String CLIENT_ID = "1011464737889-n9avi9id8fur78jh3kqqctp9lijphq2n.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "ea78y_rPz3G4kwIV3yAF99aG";
    private static FileDataStoreFactory dataStoreFactory;
    private static HttpTransport httpTransport;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static com.google.api.services.calendar.Calendar client;

    private Calendar agendumCalendar;

    // These are blocking queues to ease the producer/consumer problem
    private static final ArrayBlockingQueue<Task> addEventConcurrentQueue = new ArrayBlockingQueue<Task>(200);
    private static final ArrayBlockingQueue<Task> deleteEventConcurrentQueue = new ArrayBlockingQueue<Task>(200);

    public SyncProviderGoogle() {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (IOException var3) {
            System.err.println(var3.getMessage());
        } catch (Throwable var4) {
            var4.printStackTrace();
        }
    }

    @Override
    public void start() {
        logger.info("Initializing Google Calendar Sync");
        try {
            Credential t = authorize();
            client = (new com.google.api.services.calendar.Calendar.Builder(httpTransport, JSON_FACTORY, t)).setApplicationName("Agendum").build();
            agendumCalendar = getAgendumCalendar();
            
            syncManager.setSyncStatus(Sync.SyncStatus.RUNNING);

            // Process add & delete consumers into their own separate thread.
            Executors.newSingleThreadExecutor().execute(() -> processAddEventQueue());
            Executors.newSingleThreadExecutor().execute(() -> processDeleteEventQueue());
        } catch (IOException var3) {
            System.err.println(var3.getMessage());
        } catch (Throwable var4) {
            var4.printStackTrace();
        }
    }

    @Override
    public void startIfNeeded() {
        logger.info("Checking if Google Calendar needs to be started");
        if (DATA_STORE_CREDENTIAL.exists()) {
            logger.info("Credentials, starting Google Calendar");
            start();
        }
    }

    @Override
    public void stop() {
        logger.info("Stopping Google Calendar Sync");
        DATA_STORE_CREDENTIAL.delete();
        syncManager.setSyncStatus(Sync.SyncStatus.NOTRUNNING);
    }

    @Override
    public void addNewEvent(Task task) {
        try {
            addEventConcurrentQueue.put(task);
            logger.info("Task added to GCal add queue");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEvent(Task task) {
        try {
            deleteEventConcurrentQueue.put(task);
            logger.info("Task added to GCal delete queue");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Authorize with Google Calendar
     * @return Credentail
     * @throws Exception
     */
    private Credential authorize() throws Exception {
        GoogleClientSecrets.Details details = new GoogleClientSecrets.Details();
        details.setClientId(CLIENT_ID);
        details.setClientSecret(CLIENT_SECRET);

        GoogleClientSecrets clientSecrets = new GoogleClientSecrets().setInstalled(details);

        GoogleAuthorizationCodeFlow flow = (new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, Collections.singleton("https://www.googleapis.com/auth/calendar"))).setDataStoreFactory(dataStoreFactory).build();
        return (new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())).authorize("user");
    }

    /**
     * Returns a new "Agendum Calendar" in the authenticated user.
     * If a calendar with the same name doesn't already exist, it creates one.
     * @return
     * @throws IOException
     */
    private Calendar getAgendumCalendar() throws IOException {
        CalendarList feed = client.calendarList().list().execute();
        logger.info("Searching for Agnendum Calendar");

        for (CalendarListEntry entry : feed.getItems()) {
            if (entry.getSummary().equals(CALENDAR_NAME)) {
                logger.info(CALENDAR_NAME + " found");
                Calendar calendar = client.calendars().get(entry.getId()).execute();
                logger.info(calendar.toPrettyString());
                return calendar;
            }

        }

        logger.info(CALENDAR_NAME + " not found, creating it");
        Calendar entry = new Calendar();
        entry.setSummary(CALENDAR_NAME);
        Calendar calendar = client.calendars().insert(entry).execute();
        logger.info(calendar.toPrettyString());
        return calendar;
    }

    /**
     * Delete Agendum calendar in Google Calendar.
     */
    public void deleteAgendumCalendar() {
        try {
            CalendarList feed = client.calendarList().list().execute();
            logger.info("Deleting Agendum calendar");

            for (CalendarListEntry entry : feed.getItems()) {
                if (entry.getSummary().equals(CALENDAR_NAME)) {
                    client.calendars().delete(entry.getId()).execute();
                }

            }
        } catch (IOException e)
             {e.printStackTrace();
        }
    }

    /**
     * A event loop that continuously processes the add event queue.
     *
     * `.take();` is a blocking call so it waits until there is something
     * in the array before returning.
     *
     * This method should only be called on non-main thread.
     */
    private void processAddEventQueue() {
        while (true) {
            try {
                Task task = addEventConcurrentQueue.take();
                Date startDate = Date.from(task.getStartDateTime().get().atZone(ZoneId.systemDefault()).toInstant());
                Date endDate = Date.from(task.getEndDateTime().get().atZone(ZoneId.systemDefault()).toInstant());
                String id = Integer.toString(abs(task.syncCode()));

                EventDateTime startEventDateTime = new EventDateTime().setDateTime(new DateTime(startDate));
                EventDateTime endEventDateTime = new EventDateTime().setDateTime(new DateTime(endDate));

                Event newEvent = new Event();
                newEvent.setSummary(String.valueOf(task.getName()));
                newEvent.setStart(startEventDateTime);
                newEvent.setEnd(endEventDateTime);
                newEvent.setId(id);

                Event result = client.events().insert(agendumCalendar.getId(), newEvent).execute();
                logger.info(result.toPrettyString());

                logger.info("Task processed from GCal add queue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * A event loop that continuously processes the delete event queue.
     *
     * `.take();` is a blocking call so it waits until there is something
     * in the array before returning.
     *
     * This method should only be called on non-main thread.
     */
    private void processDeleteEventQueue() {
        while (true) {
            try {
                Task task = deleteEventConcurrentQueue.take();
                String id = Integer.toString(abs(task.syncCode()));
                client.events().delete(agendumCalendar.getId(), id).execute();

                logger.info("Task added to GCal delete queue");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
