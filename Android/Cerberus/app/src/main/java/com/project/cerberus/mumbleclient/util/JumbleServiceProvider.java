package com.project.cerberus.mumbleclient.util;

import com.project.cerberus.mumbleclient.service.IPlumbleService;

/**
 * Created by andrew on 03/08/13.
 */
public interface JumbleServiceProvider {
    IPlumbleService getService();
    void addServiceFragment(JumbleServiceFragment fragment);
    void removeServiceFragment(JumbleServiceFragment fragment);
}
