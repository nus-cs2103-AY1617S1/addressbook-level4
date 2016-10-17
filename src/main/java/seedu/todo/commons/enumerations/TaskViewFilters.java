package seedu.todo.commons.enumerations;
import java.util.function.Predicate;

import seedu.todo.model.task.ImmutableTask;

public enum TaskViewFilters {
    INCOMPLETE {
        @Override
        public String toString() {
          return "incomplete";
        }
        
      },
      COMPLETED {
        @Override
        public String toString() {
          return "complete";
        }
      },
      DUE_TODAY {
        @Override
        public String toString() {
          return "due today";
        }
      },
      DUE_THIS_WEEK {
          @Override
          public String toString() {
            return "due this week";
          }
        },
      OVERDUE {
          @Override
          public String toString() {
            return "overdue";
          }
        }
     


}
