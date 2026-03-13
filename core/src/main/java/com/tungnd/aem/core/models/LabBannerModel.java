package com.tungnd.aem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL

)
public class LabBannerModel {

    @ValueMapValue(name = "label")
    private String label;

    @ValueMapValue(name = "title")
    private String title;

    @ValueMapValue(name = "description")
    private String description;

    public String getLabel() {
        return label;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
