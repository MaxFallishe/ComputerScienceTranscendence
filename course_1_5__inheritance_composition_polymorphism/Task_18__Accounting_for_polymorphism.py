# The most common mistakes related to incorrect application of inheritance:
# - confusion between inheritance ("is-a") and composition ("has-a");
# - as a criterion for the application of inheritance, there is a
#   certain classifier field (the gender of a person or any other list of fixed values), which is
#   used in the program only for statistical purposes (there is no specific logic associated with processing
#   specific field values);
# - inheritance is used to gain access to a successful implementation of the ancestor class, which is closed to
#   changes (the error here is in the poor elaboration of the ancestor class's administrative division).
#
#
# Examples of the "is-a" relationship between classes, which is unambiguously such and cannot be translated into the
# "has-a" relationship.
# 1. A shark is a fish, in this case it is impossible to say that the ratio between a fish and a shark may imply a
# "has-a" ratio.
# 2. A smartphone is an augmentation, from a human point of view, a smartphone is an augmentation, even if it \
# is not literally embedded inside.
