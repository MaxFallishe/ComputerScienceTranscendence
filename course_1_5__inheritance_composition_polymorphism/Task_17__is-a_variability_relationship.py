# The first rule of choosing between "is" and "contains" implies that if the relationship is similar to "is", but the
# type of inherited object may change according to the logic of the program, then the correct relationship will
# be "contains".

# There are often problems in choosing the relationship "is" or "contains" when there is clearly a connection
# between classes, but it is not clear how to formalize it. The difficulty here is that "contains" very rarely means
# "is" as well, but "is" quite often means "contains".

# Two verbal examples of the relationship "contains" between classes, which is similar to "is", but according
# to the above rule it is not.

# 1. "The lungs are alveoli". in fact, it would be correct - the lungs contain alveoli. Although you might think
# that the lungs are a large version of the alveoli, they actually contain them. For example, on another planet,
# it is easy to imagine a creature that also has lungs, but its alveoli as such differ in functionality.
#
# 2. "The album is a sheet". It seems that both are suitable for drawing, but it is clearly more correct
# to use the contains relation.
