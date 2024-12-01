from typing import Callable

from pymonad.reader import Compose
from pymonad.tools import curry


# The tag() partial application function, which receives two string parameters as input: the name of the HTML tag,
# and the value. This function wraps the value with a tag, taking into account the opening and closing tag,
# for example: tag('b', 'string') # <b>string</b>
@curry(2)
def wrap_tag(tag_name: str, value: str) -> str | Callable:
    template = '<{tag_name}>{value}</{tag_name}>'
    return template.format(tag_name=tag_name, value=value)


prep_tag = wrap_tag('a')
print(prep_tag('text'))  # Output: <a>text</a>


# Let's extend the tag function with the third attr parameter (dictionary type), which adds a set of properties
# to the tag (there may be several of them). Example: tag('li', {'class': 'list-group'}, 'item 23') ->
# <li class="list-group">item 23</li>
@curry(3)
def wrap_html_tag(tag_name: str, classes: dict, value: str) -> str | Callable:
    tag_template = '<{tag_name}{classes}>{value}</{tag_name}>'
    tag_classes_template = ' {key}="{value}"'

    formatted_classes = ""
    if classes:
        classes_strings: tuple[str] = (tag_classes_template.format(key=key, value=classes[key]) for key in classes)
        formatted_classes: str = "".join(classes_strings)
    return tag_template.format(tag_name=tag_name, value=value, classes=formatted_classes)


print(wrap_html_tag('li', {'class': 'list-group', 'onClick': 'actionX'}, 'item 23'))  # Output: <li class="list-group" onClick="actionX">item 23</li>
