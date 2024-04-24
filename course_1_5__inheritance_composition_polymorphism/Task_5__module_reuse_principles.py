# A full-fledged mechanism for adding a new module to the project, implying code reuse, must meet all
# five of the following principles.
#
# 1. The new module can specify some basic type, which should potentially allow parameterization by other
#    types (generalized types, generic types);
# 2. The new module can combine several functions that actively address each other;
# 3. The new module may be part of a family of modules focused on solving some common problem that cannot
#    be solved using a single module;
# 4. The new module may offer a specific implementation of the parent module, which must be selected dynamically
#    (polymorphously) -- for example, the implementation of a generalized type for a specific parameter type;
# 5. The new module can integrate the general behavior of several modules, which differ only in details.
#
# Which of these are available in Python:
# 1. Modules do not support generic types directly
# 2. It is possible to create modules that can contain functions that interact with each other
# 3. Module grouping via packages is supported
# 4. This principle is implemented at the class level
# 5. It is possible to easily import functionality from one module to another
