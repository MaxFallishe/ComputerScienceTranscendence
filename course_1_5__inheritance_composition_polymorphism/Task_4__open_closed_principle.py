# As an example of demonstrating the principle of openness-closeness, you can present an example
# with such a thing as a license.
# For example, there is a license class, and it will be a class closed for editing, because it identifies itself
# with the meaning of the license, that is, something that allows someone to use something. This concept itself is
# closed for editing, but specific license implementations or license subtypes that will already be available for
# editing (will be open). That is, some MIT license will already be open for editing, but the license inherited from
# the license only for legal entities will also be closed for editing since it is still a sufficiently general form of the essence of the license.

class License:  # close to edit
    ...


class LicenseMIT(License):  # open to edit
    ...


class LegalEntityLicense(License):  # still close to edit
    ...


class LicenseMicrosoftVolume(LegalEntityLicense):  # and now it's open for editing
    ...
