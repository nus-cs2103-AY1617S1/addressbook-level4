# About Us

We are a team based in the [School of Computing, National University of Singapore](http://www.comp.nus.edu.sg).

## Project Team

#### [Irvin Lim](https://irvinlim.com) [@irvinlim](http://github.com/irvinlim)
<img src="https://avatars3.githubusercontent.com/u/9884746" width="150"><br>

**Role**: Team Lead, Developer

* Components in charge of: 
    * [UI](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/docs/DeveloperGuide.md#ui-component)
    * [InputHandler](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/docs/DeveloperGuide.md#inputhandler-component)
* Aspects/tools in charge of: 
    * JavaFX
    * CSS
    * GUI testing
    * Code coverage
* Features implemented:
    * UI
      - `loadView` / `renderView` pattern
      - [`componentDidMount`](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/docs/DeveloperGuide.md#can-load-sub-components) pattern
    * Controllers 
      - `list` controller
      - `config` controller/view
      - `complete` / `incomplete` controller
      - `help` view (standardize `CommandDefinition`)
    * Command history (up and down arrows)
    * Disambiguator concern
* Code written: [[functional code](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/collated/main/A0139812A.md)][[test code](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/collated/test/A0139812A.md)][[docs](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/collated/test/A0139812A.md)]
* Other major contributions:
    * Restructured frontend logic to allow composition of sub-`Component`s and `MultiComponent`s, rather than `ObservableList`.
    * Restructured GUI tests to work with the new setup
    * Created comprehensive DateUtil methods
    * Abstracted comprehensive assertion methods for GUI testing of `Task`s / `Event`s

-----

#### [Louie Tan @louietyj](http://github.com/louietyj)
<img src="https://avatars1.githubusercontent.com/u/11096034" width="150"><br>

**Role**: QA Manager, Developer

* Components in charge of: 
    * [Models](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/docs/DeveloperGuide.md#model-component)
    * [Storage](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/docs/DeveloperGuide.md#storage-component)
    * [Controllers](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/docs/DeveloperGuide.md#controller-component)
* Aspects/tools in charge of: 
    * Ensuring code quality
    * Git/Integration
    * Flexi-command parsing (Tokenizer)
    * Disambiguation flow
    * Date parsing (Natty)
* Features implemented:
    * Controllers
      - `add` controller
      - `alias` / `unalias` controller
      - `clear` controller
      - `destroy` controller
      - `find` controller
      - `undo` / `redo` controller
      - `update` controller
    * Concerns
      * Tokenizer concern
      * CalendarItemFilter concern
      * DateParser concern (with Natty)
* Code written: [[functional code](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/collated/main/A0093907W.md)][[test code](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/collated/test/A0093907W.md)][[docs](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/collated/test/A0093907W.md)]
* Other major contributions:
    * Designed the entire back-end architecture (MVC pattern)
    * Designed predicate-based Model filtering to replace old filtering patterns
    * Came up with the generic parse-by-token algorithm to support all Controllers

-----

#### [Tiong Yaocong @ChaseYaoCong](http://github.com/ChaseYaoCong)
<img src="https://avatars3.githubusercontent.com/u/16850418" width="150"><br>

**Role**: HR Manager, Developer

*Yaocong is maintaining a separate, individual fork [here](https://github.com/ChaseYaoCong/main).*

* Aspects/tools in charge of: 
    * Project export
    * Documentation
* Features implemented:
    * `tag` / `untag` controller
* Code written: [[functional code](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/collated/main/A0139922Yunused.md)][[test code](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/collated/docs/A0139922Y.md)][[docs](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/collated/test/A0139922Yunused.md)]
* Other major contributions:
    * [Documentation for use cases](https://github.com/CS2103AUG2016-F11-C1/main/blob/master/docs/DeveloperGuide.md#appendix-b--use-cases)

## Project Mentor

#### [Chuan Wei Candiie](http://github.com/Candiie)
<img src="images/candiieTA.png" width="150"><br>

-----

## Zarro boogs found?

Pat us on the back at our [contact us](ContactUs.md) page, or otherwise if you have suggestions, and better still, pull requests.
