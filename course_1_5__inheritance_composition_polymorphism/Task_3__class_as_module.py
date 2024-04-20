# A class as a module means that the class acts as a basic syntactic unit, both as a separate file and
# as an autonomously compiled entity. Inheritance in this case acts primarily as a mechanism for code reuse

# In python, the concept of "class as module" is supported as follows:
# In some modules, there is literally a certain main class around which the main functionality
# of the module is implemented. For example, in the bs4 module for parsing html code, this is the BeautifulSoup class
from bs4 import BeautifulSoup

# Importing the requests library and requesting a page google.com to show in practice how the bs4 module works
import requests
requested_page = requests.get("https://google.com")

# Further, as you can see all further actions, all the functionality of the bs4 module is implemented through
# an instance of the BeautifulSoup class
soup = BeautifulSoup(requested_page.text, "html.parser")

# Here you can see how the elements are found by different parameters and the link is pulled from a certain element.
# What unites them is that they all use a common instance of the BeautifulSoup class.
gb1_html_objs = soup.find_all(class_='gb1')
first_div = soup.find('div')
imghp_link = soup.find('div').find('a').get('href')

