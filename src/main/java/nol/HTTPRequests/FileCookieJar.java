package nol.HTTPRequests;

import okhttp3.CookieJar;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import java.io.*;
import java.util.*;

public class FileCookieJar implements CookieJar {
    private final Map<String, List<Cookie>> cookieStore = new HashMap<>();
    private final File cookieFile;

    public FileCookieJar(File cookieFile) {
        this.cookieFile = cookieFile;
        try {
            // Crear el archivo si no existe
            if (!cookieFile.exists()) {
                cookieFile.createNewFile(); 
            }
            loadCookiesFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cargar cookies desde el archivo (como "cucu")
    private void loadCookiesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(cookieFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) continue;
                Cookie cookie = parseNetscapeCookie(line);
                if (cookie != null) {
                    String domainKey = cookie.domain();
                    List<Cookie> cookies = cookieStore.getOrDefault(domainKey, new ArrayList<>());
                    cookies.add(cookie);
                    cookieStore.put(domainKey, cookies);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Guardar cookies en el archivo
    private void saveCookiesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cookieFile))) {
            writer.write("# Netscape HTTP Cookie File\n");
            writer.write("# https://curl.se/docs/http-cookies.html\n\n");
            for (List<Cookie> cookies : cookieStore.values()) {
                for (Cookie cookie : cookies) {
                    writer.write(toNetscapeString(cookie) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Convertir Cookie a formato Netscape
    private String toNetscapeString(Cookie cookie) {
        return String.format("%s\t%s\t%s\t%s\t%d\t%s\t%s",
                cookie.domain(),
                "FALSE", // Ignorar subdominios
                cookie.path(),
                "FALSE", // No seguro (HTTP)
                cookie.expiresAt() / 1000, // Tiempo en segundos
                cookie.name(),
                cookie.value());
    }

    // Parsear línea del archivo a Cookie
    private Cookie parseNetscapeCookie(String line) {
        String[] parts = line.split("\t");
        if (parts.length < 7) return null;
        return new Cookie.Builder()
                .domain(parts[0])
                .path(parts[2])
                .name(parts[5])
                .value(parts[6])
                .expiresAt(Long.parseLong(parts[4]) * 1000)
                .build();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.put(url.host(), cookies);
        saveCookiesToFile();
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.getOrDefault(url.host(), new ArrayList<>());
    }
}