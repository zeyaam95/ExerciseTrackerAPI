import React from "react";

export const Theme = {
    Dark: "Dark",
    Light: "Light"
};

export const ThemeContext = React.createContext({
    theme: Theme.Dark,
    setTheme: (theme) => console.warn('no theme provider')
});
