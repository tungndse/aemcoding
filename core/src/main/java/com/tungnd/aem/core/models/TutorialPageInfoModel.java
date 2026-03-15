package com.tungnd.aem.core.models;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class TutorialPageInfoModel {

    @Self
    private Resource resource;

    @ScriptVariable
    private Page currentPage;

    @ValueMapValue
    private String theme;

    public String getPagePath() {
        return currentPage != null ? currentPage.getPath() : "";
    }

    public String getPageTitle() {
        return currentPage != null ? currentPage.getTitle() : "";
    }

    public String getResourcePath() {
        return resource != null ? resource.getPath() : "";
    }

    public String getResourceType() {
        return resource != null ? resource.getResourceType() : "";
    }

    public String getTemplatePath() {
        return currentPage != null
                ? currentPage.getProperties().get("cq:template", "")
                : "";
    }

    public String getParentPath() {
        if (currentPage != null && currentPage.getParent() != null)
            return currentPage.getParent().getPath();

        return "";
    }

    public String getTheme() {
        return theme != null && !theme.isEmpty() ? theme : "light";
    }


}
