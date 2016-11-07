package seedu.whatnow.model.task;

import org.junit.Test;

import seedu.whatnow.commons.exceptions.IllegalValueException;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertEquals;

public class TaskTimeTest {
    
  @Test
  public void TaskTimeConstructionn_dateToday_instantiated() throws IllegalValueException {
      TaskTime testTime = new TaskTime("11:59pm", null, null, "today", null, null);
      assertTrue(testTime.getDate() != null);
  }
  
  @Test
  public void TaskTimeConstruction_dateTomorrow_instantiated() throws IllegalValueException {
      TaskTime testTime = new TaskTime("11:59pm", null, null, "tomorrow", null, null);
      assertTrue(testTime.getDate() != null);
  }
  
  @Test
  public void TaskTimeConstruction_dateNull_instantiated() throws IllegalValueException {
      TaskTime testTime = new TaskTime("11:59pm", null, null, null, null, null);
      assertTrue(testTime.getDate() != null);
  }

  @Test
  public void TaskTimeConstruction_oneDateTwoTime_instantiated() throws IllegalValueException {
      TaskTime testTime = new TaskTime(null, "12:00am", "11:59pm", "12/12/2222", null, null);
      assertTrue(testTime.getDate() != null);
  }

  @Test
  public void TaskTimeConstruction_invalidDate_instantiationFailed() {
      try {
          new TaskTime("11:59pm", null, null, "11111111", null, null);
      } catch (IllegalValueException e) {
          assertEquals(e.getMessage(), TaskTime.INVALID_DATE_MESSAGE);
      }
  }
  
  @Test
  public void TaskTimeConstruction_invalidDateRange_instantiationFailed() {
      try {
          new TaskTime(null, null, null, "today", "12/12/2222", "10/12/2222");
      } catch (IllegalValueException e) {
          assertEquals(e.getMessage(), TaskTime.INVALID_DATE_RANGE_MESSAGE);
      }
  }
  
  @Test
  public void TaskTimeConstruction_invalidTime_instantiationFailed() {
      try {
          new TaskTime("23:00pm", null, null, "today", null, null);
      } catch (IllegalValueException e) {
          assertEquals(e.getMessage(), TaskTime.INVALID_TIME_MESSAGE);
      }
  }
  
  @Test
  public void TaskTimeConstruction_invalidTimeRange_instantiationFailed() {
      try {
          new TaskTime(null, "04:00pm", "01:00pm", "today", null, null);
      } catch (IllegalValueException e) {
          assertEquals(e.getMessage(), TaskTime.INVALID_TIME_RANGE_MESSAGE);
      }
  }
  
  @Test
  public void isValidTimeSeq_dateTodayTimeInvalid_invalidTime() {
      try {
          new TaskTime("12:00am", null, null, "today", null, null);
      } catch (IllegalValueException e) {
          assertEquals(e.getMessage(), TaskTime.INVALID_TIME_MESSAGE);
      }
  }
  
  @Test
  public void isValidTimeSeq_twoDateOneTimeValid_validTime() throws IllegalValueException {
      TaskTime test = new TaskTime("12:00am", null, null, null, "12/12/2222", "14/12/2222");
      assertTrue("12:00am".equals(test.getTime()));
  }
  
  @Test
  public void appendTodayorTomorrow_currTimeLaterThanInput_validTime() throws IllegalValueException {
      TaskTime test = new TaskTime("12:00am", null, null, null, null, null);
      assertTrue("12:00am".equals(test.getTime()));
  }
  
  @Test
  public void isValidNumTime_invalidStartTimeEndTimeFormat_invalidTimeRange() {
      try {
          new TaskTime(null, "23:00pm", "23:59pm", "today", null, null);
      } catch (IllegalValueException e) {
          assertEquals(e.getMessage(), TaskTime.INVALID_TIME_RANGE_MESSAGE);
      }
  }
  
  @Test
  public void isValidNumTime_startTimeLaterThanEndTime_invalidTimeRange() {
      try {
          new TaskTime(null, "11:59pm", "11:00pm", null, "12/12/2222", "14/12/2222");
      } catch (IllegalValueException e) {
          assertEquals(e.getMessage(), TaskTime.INVALID_TIME_RANGE_MESSAGE);
      }
  }
  
  @Test
  public void isValidNumTime_startTimeLaterThanEndTimeForSameDate_invalidTimeRange() {
      try {
          new TaskTime(null, "11:59pm", "11:00pm", null, "12/12/2222", "12/12/2222");
      } catch (IllegalValueException e) {
          assertEquals(e.getMessage(), TaskTime.INVALID_TIME_RANGE_MESSAGE);
      }
  }
  
  @Test
  public void autoCorrectInput_currTimeLaterThanInputTime_inputAutoCorrected() throws IllegalValueException {
      TaskTime test = new TaskTime(null, "11:58pm", "11:59pm", null, null, null);
      assertTrue(test.getDate() != null);
  }
  
  @Test
  public void autoCorrectInput_dateToday_inputAutoCorrected() throws IllegalValueException {
      TaskTime test = new TaskTime(null, "11:58pm", "11:59pm", "today", null, null);
      assertTrue(test.getDate() != null);
  }
  
  @Test
  public void autoCorrectInput_dateTomorrow_inputAutoCorrected() throws IllegalValueException {
      TaskTime test = new TaskTime(null, "11:58pm", "11:59pm", "tomorrow", null, null);
      assertTrue(test.getDate() != null);
  }
  
  @Test
  public void autoCorrectInput_dateTodayTimeBeforeCurrent_() throws IllegalValueException {
      try {
          new TaskTime(null, "12:00am", "12:01am", "today", null, null);
      } catch (IllegalValueException e) {
          assertEquals(e.getMessage(), TaskTime.INVALID_TIME_RANGE_MESSAGE);
      }
  }
  
  @Test
  public void TaskTimeConstructor_twoDatetwoTime_corretlyInitialised() throws IllegalValueException {
      TaskTime test = new TaskTime("11:57pm", "11:58pm", "11:59pm", "10/12/2222", "12/12/2222", "14/12/2222");
      assertEquals("11:58pm", test.getStartTime());
      assertEquals("11:59pm", test.getEndTime());
      assertEquals("12/12/2222", test.getStartDate());
      assertEquals("14/12/2222", test.getEndDate());
      assertEquals("null 12/12/2222 14/12/2222 11:57pm 11:58pm 11:59pm", test.getFullString());
  }
}
