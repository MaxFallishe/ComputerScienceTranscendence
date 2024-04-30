# Are there situations where connections between modules should be made public?
# The connections between modules can be public, provided that we want to enable the end user not only to understand
# the structure of the module connections, but we assume that he will have the opportunity to rebuild part
# of this structure for himself.
#
# What metrics would you suggest for quantifying the principles of module organization?
# I would suggest the following metrics:
# * The ratio of the number of modules to the number of methods for these modules, if the ratio is too large or too
#   small, then the modules are organized inefficiently
# * The amount of mutual use of modules - the more modules use ready-made functionality from other modules, the better
#
# If you developed programs that had at least 3-5 classes, how would you rate their modularity by these metrics?
# As part of my experience, I calculated the above criteria for a project to develop a connector in the Python SDK.
# According to the first criterion, the ratio is about 1 to 5, that is, there are about five methods in each module,
# which is a good indicator. According to the second indicator, the value is 3 imported modules in each new script,
# which is a good average value in my opinion, but there is potential to increase this indicator.
