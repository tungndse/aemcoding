package com.tungnd.aem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL

)
public class TutorialBannerModel {

    @ValueMapValue(name = "label")
    private String label;
    @ValueMapValue(name = "title")
    private String title;
    @ValueMapValue(name = "description")
    private String description;
    @ValueMapValue(name = "text")
    private String legacyDescription;

    public String getLabel() {
        return label != null ? label : "";
    }

    public String getTitle() {
        return title != null ? title : "";
    }

    public String getDescription() {
        if (description != null && !description.isEmpty()) {
            return description;
        }

        return legacyDescription != null ? legacyDescription : "";
    }
}
