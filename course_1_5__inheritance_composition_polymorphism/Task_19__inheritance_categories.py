# In the OOP model, there are about a dozen different types of inheritance that differ in their semantics.
# Different OOP methods offer their own combinations of different types of inheritance and, accordingly, seriously
# different approaches to domain modeling and the formation of class hierarchies.
#
# 1. Example of restriction inheritance:
# This type of inheritance is very similar to the basic type of subtyping. As a rule, semantically it does not refer
# to classes, but to instances, physical objects, on which certain measurable restrictions are imposed. An example is
# the parent class "Tractor" with heirs in the form of "FarmTractor" and "ConstructionTractor". Each of these
# classes does not add new functionality to the tractor, but rather restricts its use.
#
# 2.Example of extension inheritance:
# An example of inheritance with an extension is the ratio of the "Glyph" class, the heir of which is the "Symbol"
# class. In this case, the Glyph has a definition - a symbol carved or inscribed on a stone or tree. At the same time,
# the meaning of the Symbol is broader, including it can be in
