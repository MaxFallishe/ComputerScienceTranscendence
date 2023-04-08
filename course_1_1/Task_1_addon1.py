from course_1_1.Task_1 import *
from typing import Union


def sum_llist(llist_1: LinkedList, llist_2: LinkedList) -> Union[LinkedList, None]:
    if llist_1.len() != llist_2.len():
        return None
    result_llist = LinkedList()
    node_llist_1 = llist_1.head
    node_llist_2 = llist_2.head
    while node_llist_1 is not None:
        nodes_sum = node_llist_1.value + node_llist_2.value
        result_llist.add_in_tail(Node(nodes_sum))
        node_llist_1 = node_llist_1.next
        node_llist_2 = node_llist_2.next
    return result_llist
