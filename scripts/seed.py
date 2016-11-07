import time
import datetime
import random
import radar
import faker
import pyautogui

DEBUG = False

VERBS = ["Meet", "Lunch with", "Dinner with", "Call", "Kill", "Meeting with", "Talk to"]
TAGS = ['meditation', 'summertime', 'goodtime', 'comedy', 'homesweethome', 'gymtime', 'games', 'monochrome']

fake = faker.Faker()

def random_time():
    MIN = datetime.datetime.now() - datetime.timedelta(days = 3)
    MAX = datetime.datetime.now() + datetime.timedelta(days = 7 * 2)
    return radar.random_datetime(MIN, MAX).strftime('%Y-%m-%d %H:%M')

def random_time_pair():
    MIN = datetime.datetime.now() - datetime.timedelta(days = 3)
    MAX = datetime.datetime.now() + datetime.timedelta(days = 7 * 2)
    start = radar.random_datetime(MIN, MAX)
    end = start + datetime.timedelta(hours = 2)
    return start.strftime('%Y-%m-%d %H:%M'), \
           end.strftime('%Y-%m-%d %H:%M')

def enter_command(command):
    if DEBUG:
        print(command)
    else:            
        pyautogui.typewrite(command)
        pyautogui.press('enter')

def generate_tasks(n):
    for i in range(n):
        command = 'add task "%s" by "%s"' \
                  % (fake.bs().capitalize(), random_time())
        enter_command(command)

def generate_floating_tasks(n):
    for i in range(n):
        command = 'add task "%s"' \
                  % (fake.bs().capitalize())
        enter_command(command)

def complete_tasks(n, max_n):
    assert n <= max_n
    for i in range(n):
        max_n -= 1
        command = 'complete %s' % random.randint(1, max_n)
        enter_command(command)

def generate_events(n):
    for i in range(n):
        time_pair = random_time_pair()
        command = 'add event "%s %s" from "%s" to "%s"' \
                  % (random.choice(VERBS), fake.name(), time_pair[0], time_pair[1])
        enter_command(command)

def tag(max_i, max_tags):
    for i in range(max_i):
        num_tags = random.randint(0, max_tags)
        tags = random.sample(TAGS, num_tags)
        for tag in tags:
            command = "tag %s %s" % (i, tag)
            enter_command(command)

def run():
    NUM_DEADLINED_TASKS = 50
    NUM_FLOATING_TASKS = 5
    NUM_COMPLETE_TASKS = 25
    NUM_EVENTS = 50
    NUM_MAX_TAGS = 3
    
    PREAMBLE_TIME = 3

    for i in range(PREAMBLE_TIME):
        print(PREAMBLE_TIME - i)
        time.sleep(1)

    generate_tasks(NUM_DEADLINED_TASKS)
    generate_floating_tasks(NUM_FLOATING_TASKS)
    complete_tasks(NUM_COMPLETE_TASKS, NUM_DEADLINED_TASKS + NUM_FLOATING_TASKS)
    generate_events(NUM_EVENTS)
    tag(NUM_DEADLINED_TASKS + NUM_FLOATING_TASKS + NUM_EVENTS, NUM_MAX_TAGS)
