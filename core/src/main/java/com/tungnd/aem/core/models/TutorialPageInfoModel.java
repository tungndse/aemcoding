package com.tungnd.aem.core.models;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
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

    @ValueMapValue
    private String theme;

    /*@ScriptVariable
    private Page currentPage;

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
    }*/

    private Page getCurrentPage() {
        if (resource == null) {
            return null;
        }

        ResourceResolver resolver = resource.getResourceResolver();
        PageManager pageManager = resolver.adaptTo(PageManager.class);

        return pageManager != null ? pageManager.getContainingPage(resource) : null;
    }

    public String getPagePath() {
        Page currentPage = getCurrentPage();
        return currentPage != null ? currentPage.getPath() : "";
    }

    public String getPageTitle() {
        Page currentPage = getCurrentPage();
        return currentPage != null ? currentPage.getTitle() : "";
    }

    public String getResourcePath() {
        return resource != null ? resource.getPath() : "";
    }

    public String getResourceType() {
        return resource != null ? resource.getResourceType() : "";
    }

    public String getTemplatePath() {
        Page currentPage = getCurrentPage();
        return currentPage != null
                ? currentPage.getProperties().get("cq:template", "")
                : "";
    }

    public String getParentPath() {
        Page currentPage = getCurrentPage();
        if (currentPage != null && currentPage.getParent() != null) {
            return currentPage.getParent().getPath();
        }
        return "";
    }

    public String getTheme() {
        return theme != null && !theme.isEmpty() ? theme : "light";
    }


}
