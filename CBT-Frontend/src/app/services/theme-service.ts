import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private currentTheme: string = 'lightTheme';
  setTheme() {
    if (this.currentTheme === 'lightTheme') {
      (window as any).monaco.editor.setTheme('leetcodeTheme');
      this.currentTheme = 'leetcodeTheme';
    } else {
      (window as any).monaco.editor.setTheme('lightTheme');
      this.currentTheme = 'lightTheme';
    }
  }
  ApplyIntialtheme(theme: 'lightTheme' | 'leetcodeTheme') {
    (window as any).monaco.editor.setTheme(theme);
  }
}
