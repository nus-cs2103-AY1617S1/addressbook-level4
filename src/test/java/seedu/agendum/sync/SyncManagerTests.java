package seedu.agendum.sync;

import org.junit.Before;
import org.junit.Test;
import seedu.agendum.model.task.Task;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;

// @@author A0003878Y
public class SyncManagerTests {
    private SyncManager syncManager;
    private SyncProvider mockSyncProvider;

    @Before
    public void setUp() {
        mockSyncProvider = mock(SyncProvider.class);
        syncManager = new SyncManager(mockSyncProvider);
    }

    @Test
    public void syncManager_setStatusRunning_expectRunning() {
        syncManager.setSyncStatus(Sync.SyncStatus.RUNNING);
        assertEquals(syncManager.getSyncStatus(),Sync.SyncStatus.RUNNING);
    }

    @Test
    public void syncManager_setStatusNotRunning_expectNotRunning() {
        syncManager.setSyncStatus(Sync.SyncStatus.NOTRUNNING);
        assertEquals(syncManager.getSyncStatus(),Sync.SyncStatus.NOTRUNNING);
    }

    @Test
    public void syncManager_startSyncing_expectSyncProviderStart() {
        syncManager.startSyncing();
        verify(mockSyncProvider).start();
    }

    @Test
    public void syncManager_stopSyncing_expectSyncProviderStop() {
        syncManager.stopSyncing();
        verify(mockSyncProvider).stop();
    }

    @Test
    public void syncManager_addEventWithStartAndEndTime_expectSyncProviderAdd() {
        Task mockTask = mock(Task.class);
        Optional<LocalDateTime> fakeTime = Optional.of(LocalDateTime.now());

        when(mockTask.getStartDateTime()).thenReturn(fakeTime);
        when(mockTask.getEndDateTime()).thenReturn(fakeTime);

        syncManager.setSyncStatus(Sync.SyncStatus.RUNNING);
        syncManager.addNewEvent(mockTask);

        verify(mockSyncProvider).addNewEvent(mockTask);
    }

    @Test
    public void syncManager_addEventWithStartTime_expectNoSyncProviderAdd() {
        Task mockTask = mock(Task.class);
        Optional<LocalDateTime> fakeTime = Optional.of(LocalDateTime.now());
        Optional<LocalDateTime> empty = Optional.empty();

        when(mockTask.getStartDateTime()).thenReturn(empty);
        when(mockTask.getEndDateTime()).thenReturn(fakeTime);


        syncManager.setSyncStatus(Sync.SyncStatus.RUNNING);
        syncManager.addNewEvent(mockTask);

        verify(mockSyncProvider, never()).addNewEvent(mockTask);
    }

    @Test
    public void syncManager_addEventWithEndTime_expectNoSyncProviderAdd() {
        Task mockTask = mock(Task.class);
        Optional<LocalDateTime> fakeTime = Optional.of(LocalDateTime.now());
        Optional<LocalDateTime> empty = Optional.empty();

        when(mockTask.getStartDateTime()).thenReturn(fakeTime);
        when(mockTask.getEndDateTime()).thenReturn(empty);

        syncManager.setSyncStatus(Sync.SyncStatus.RUNNING);
        syncManager.addNewEvent(mockTask);

        verify(mockSyncProvider, never()).addNewEvent(mockTask);
    }

    @Test
    public void syncManager_addEventWithNoTime_expectNoSyncProviderAdd() {
        Task mockTask = mock(Task.class);
        Optional<LocalDateTime> empty = Optional.empty();

        when(mockTask.getStartDateTime()).thenReturn(empty);
        when(mockTask.getEndDateTime()).thenReturn(empty);

        syncManager.setSyncStatus(Sync.SyncStatus.RUNNING);
        syncManager.addNewEvent(mockTask);

        verify(mockSyncProvider, never()).addNewEvent(mockTask);
    }

    @Test
    public void syncManager_addEventWithSyncManagerNotRunning_expectNoSyncProviderAdd() {
        Task mockTask = mock(Task.class);

        syncManager.setSyncStatus(Sync.SyncStatus.NOTRUNNING);
        syncManager.addNewEvent(mockTask);

        verify(mockSyncProvider, never()).addNewEvent(mockTask);
    }

    @Test
    public void syncManager_deleteEventWithSyncManagerRunning_expectSyncProviderDelete() {
        Task mockTask = mock(Task.class);

        syncManager.setSyncStatus(Sync.SyncStatus.RUNNING);
        syncManager.deleteEvent(mockTask);

        verify(mockSyncProvider).deleteEvent(mockTask);
    }

    @Test
    public void syncManager_deleteEventWithSyncManagerNotRunning_expectNoSyncProviderDelete() {
        Task mockTask = mock(Task.class);

        syncManager.setSyncStatus(Sync.SyncStatus.NOTRUNNING);
        syncManager.deleteEvent(mockTask);

        verify(mockSyncProvider, never()).deleteEvent(mockTask);
    }
}
