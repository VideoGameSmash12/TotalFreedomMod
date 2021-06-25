package me.totalfreedom.totalfreedommod.httpd.module;

import me.totalfreedom.totalfreedommod.admin.Admin;
import me.totalfreedom.totalfreedommod.httpd.NanoHTTPD;

public class Module_admins extends HTTPDModule
{

    public Module_admins(NanoHTTPD.HTTPSession session)
    {
        super(session);
    }

    @Override
    public NanoHTTPD.Response getResponse()
    {

        return new NanoHTTPD.Response(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT,
                "Error 404: Not Found - i have to re-work this");
        /*File adminFile = new File(plugin.getDataFolder(), Admin.CONFIG_FILENAME);
        if (adminFile.exists())
        {
            final String remoteAddress = socket.getInetAddress().getHostAddress();
            if (!isAuthorized(remoteAddress))
            {
                return new NanoHTTPD.Response(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT,
                        "You may not view the admin list. Your IP, " + remoteAddress + ", is not registered to an admin on the server.");
            }
            else
            {
                return HTTPDaemon.serveFileBasic(new File(plugin.getDataFolder(), Admin.CONFIG_FILENAME));
            }

        }
        else
        {
            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT,
                    "Error 404: Not Found - The requested resource was not found on this server.");
        }*/
    }

    private boolean isAuthorized(String remoteAddress)
    {
        Admin entry = plugin.al.getEntryByUuid(plugin.pl.getDataByIp(remoteAddress).getUuid());
        return entry != null && entry.isActive();
    }
}