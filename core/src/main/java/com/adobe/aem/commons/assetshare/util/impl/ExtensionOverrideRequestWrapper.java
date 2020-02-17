/*
 * Asset Share Commons
 *
 * Copyright (C) 2018 Adobe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.adobe.aem.commons.assetshare.util.impl;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.wrappers.SlingHttpServletRequestWrapper;

/**
 * SlingHttpServletRequest Wrapper that allows the force setting (or removal) of the original requests extension.
 *
 * This is useful when doing internal request dispatcher (include or forward) as there is no way to reset the extension via RequestDispatcherOptions.
 */
public class ExtensionOverrideRequestWrapper extends SlingHttpServletRequestWrapper {
    private final String extension;

    /**
     * @param wrappedRequest the request to wrap;
     * @param extension the extension to force. Set to null for no extension;
     */
    public ExtensionOverrideRequestWrapper(SlingHttpServletRequest wrappedRequest, String extension) {
        super(wrappedRequest);
        this.extension = extension;
    }

    @Override
    public RequestPathInfo getRequestPathInfo() {
        return new RequestPathInfoWrapper(super.getRequestPathInfo(), extension);
    }

    private class RequestPathInfoWrapper implements RequestPathInfo {
        private final RequestPathInfo requestPathInfo;
        private final String extension;

        public RequestPathInfoWrapper(RequestPathInfo requestPathInfo, String extension) {
            this.requestPathInfo = requestPathInfo;
            this.extension = extension;
        }

        public String getResourcePath() {
            return requestPathInfo.getResourcePath();
        }

        public String getExtension() {
            return extension;
        }

        public String getSelectorString() {
            return requestPathInfo.getSelectorString();
        }

        public String[] getSelectors() {
            return requestPathInfo.getSelectors();
        }

        public String getSuffix() {
            return requestPathInfo.getSuffix();
        }

        public Resource getSuffixResource() {
            return requestPathInfo.getSuffixResource();
        }
    }
}