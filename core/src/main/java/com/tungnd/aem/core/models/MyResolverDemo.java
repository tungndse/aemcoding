package com.tungnd.aem.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class MyResolverDemo {

    @SlingObject
    private Resource currentResource;

    @SlingObject
    private ResourceResolver resourceResolver;

    @ValueMapValue
    private String targetPath;

    public boolean hasTargetPath() {
        return (StringUtils.isNotBlank(targetPath));
    }

    public String getCurrentResourcePath() {
        return currentResource.getPath();
    }

    public String getCurrentResourceType() {
        return currentResource.getResourceType();
    }

    public String getTargetResourcePath() {
        if (StringUtils.isBlank(targetPath)) return "-";
        Resource targetResource = resourceResolver.getResource(targetPath);
        return targetResource != null ? targetResource.getPath() : "Not found";
    }

    public String getTargetResourceType() {
        if (StringUtils.isBlank(targetPath)) return "-";
        Resource targetResource = resourceResolver.getResource(targetPath);
        return targetResource != null ? targetResource.getResourceType() : "Not found";
    }

    public String getContainingPagePath() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        if (pageManager == null) return "-";
        Page page = pageManager.getContainingPage(currentResource);
        return page != null ? page.getPath() : "Not found";
    }

    public String getResolvedPath() {
        if (StringUtils.isBlank(targetPath)) return "-";
        Resource resolved = resourceResolver.resolve(targetPath);
        return resolved.getPath();
    }




}
