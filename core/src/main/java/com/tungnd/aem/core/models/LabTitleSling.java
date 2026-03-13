package com.tungnd.aem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LabTitleSling {

    @ValueMapValue
    private String label;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String text;

    public String getLabel() {
        return label;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
