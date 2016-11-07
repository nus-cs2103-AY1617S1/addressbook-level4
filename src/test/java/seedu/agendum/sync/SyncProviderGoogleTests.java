package seedu.agendum.sync;

import org.junit.*;
import org.junit.runners.MethodSorters;
import seedu.agendum.commons.exceptions.IllegalValueException;
import seedu.agendum.model.task.Name;
import seedu.agendum.model.task.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static junit.framework.TestCase.assertFalse;
import static org.mockito.Mockito.*;
import static seedu.agendum.commons.core.Config.DEFAULT_DATA_DIR;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// @@author A0003878Y
public class SyncProviderGoogleTests {
    private static final File DATA_STORE_CREDENTIAL = new File(DEFAULT_DATA_DIR + "StoredCredential");

    private static final List<File> DATA_STORE_TEST_CREDENTIALS = Arrays.asList(
            new File("cal/StoredCredential_1"),
            new File("cal/StoredCredential_2"),
            new File("cal/StoredCredential_3")
            );

    private static final SyncProviderGoogle syncProviderGoogle = spy(new SyncProviderGoogle());
    private static final SyncManager mockSyncManager = mock(SyncManager.class);
    private static final Task mockTask = mock(Task.class);

    @BeforeClass
    public static void setUp() {
        copyTestCredentials();

        try {
            Optional<LocalDateTime> fakeTime = Optional.of(LocalDateTime.now());
            Name fakeName = new Name("AGENDUMTESTENGINE");
            int minId = 99999;
            int maxId = 9999999;
            Random r = new Random();

            when(mockTask.getStartDateTime()).thenReturn(fakeTime);
            when(mockTask.getEndDateTime()).thenReturn(fakeTime);
            when(mockTask.getName()).thenReturn(fakeName);
            when(mockTask.syncCode()).thenReturn(r.nextInt((maxId - minId) + 1) + minId);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        syncProviderGoogle.setManager(mockSyncManager);
        syncProviderGoogle.start();
    }

    @AfterClass
    public static void tearDown() {
        deleteCredential();
    }

    public static void copyTestCredentials() {
        try {
            deleteCredential();
            Files.copy(getRandomCredential().toPath(), DATA_STORE_CREDENTIAL.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCredential() {
        DATA_STORE_CREDENTIAL.delete();
    }

    private static File getRandomCredential() {
        int r = new Random().nextInt(DATA_STORE_TEST_CREDENTIALS.size());
        return DATA_STORE_TEST_CREDENTIALS.get(r);
    }

    @Test
    public void syncProviderGoogle_start_createCalendar() {
        reset(syncProviderGoogle);
        syncProviderGoogle.deleteAgendumCalendar();
        syncProviderGoogle.start();

        // Verify if Sync Manager's status was changed
        verify(mockSyncManager, atLeastOnce()).setSyncStatus(Sync.SyncStatus.RUNNING);
    }

    @Test
    public void syncProviderGoogle_startIfNeeded_credentialsFound() {
        reset(syncProviderGoogle);
        syncProviderGoogle.startIfNeeded();

        // Verify Sync Provider did start
        verify(syncProviderGoogle).start();
    }

    @Test
    public void syncProviderGoogle_startIfNeeded_credentialsNotFound() {
        reset(syncProviderGoogle);
        deleteCredential();
        syncProviderGoogle.startIfNeeded();

        // Verify Sync Provider should not start
        verify(syncProviderGoogle, never()).start();
    }

    @Test
    public void syncProviderGoogle_stop_successful() {
        reset(mockSyncManager);
        syncProviderGoogle.stop();

        // Verify sync status changed
        verify(mockSyncManager).setSyncStatus(Sync.SyncStatus.NOTRUNNING);
        assertFalse(DATA_STORE_CREDENTIAL.exists());
    }

    @Test
    public void syncProviderGoogle_addEvent_successful() {
        syncProviderGoogle.addNewEvent(mockTask);
    }

    @Test
    public void syncProviderGoogle_deleteEvent_successful() {
        syncProviderGoogle.deleteEvent(mockTask);
    }

}
