# Abstraction example:
# When a certain class was created as a universal solution, however, as the project develops, it turns out that this is
# a specific case, and it is better to add an ancestor to this class and organize inheritance.
# This technique is called abstraction.
# ACTUAL EXAMPLE
# In a project, you give users "permission to download" something - a file, a product, etc. However,
# you soon realize that this download permission is essentially a special case of a one-time license, and now that
# you have a large number of different B2B clients, it is the right decision to add the ancestor "License" and insert
# the current class as a special case.
#
#
# Factorication example:
# When several seemingly unrelated classes turn out to be only special cases of a single concept.
# Then a common parent is added to them - this is called factorization.
# ACTUAL EXAMPLE:
# Suppose your company develops software for a smart home, and you create a separate class for the door, window and
# hatch (to the attic). But then it comes to an understanding that all these entities (window, door, hatch) are some kind of portals
# to other rooms/spaces.  And now you can add a common parent to them, because their functionality is very similar
# in the end.
