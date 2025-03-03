import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ObservorService {
  private listeners: { [key: string]: Function[] } = {};

  // Method to subscribe to an event
  on(event: string, callback: Function): void {
    if (!this.listeners[event]) {
      this.listeners[event] = [];
    }
    this.listeners[event].push(callback);
  }

  // Method to emit an event
  emit(event: string, ...args: any[]): void {
    if (!this.listeners[event]) {
      return;
    }
    this.listeners[event].forEach((callback: Function) => {
      callback(...args);
    });
  }
}