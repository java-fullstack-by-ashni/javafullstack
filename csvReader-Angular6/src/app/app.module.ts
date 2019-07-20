import { NgModule }                 from '@angular/core';
import { Routes, RouterModule }     from '@angular/router';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { BrowserModule }            from '@angular/platform-browser';
import { AppComponent }             from './app.component';
import { appRoutes }                from './app.routes'; 
import { CsvFileModule } from './readCsvFile/CsvFile.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
    //put all your modules here
    //The imports key in the context of an @NgModule defines additional modules 
    //that will be imported into the current module
    imports: [ 
		BrowserModule,
	    CsvFileModule,
        RouterModule,
		RouterModule.forRoot(appRoutes, {
			useHash: true
		}),
		BrowserAnimationsModule
	],
    // put all your components / directives / pipes here
    declarations:[
        AppComponent
    ],

    // put all your services here
    providers: [
      {provide: LocationStrategy, useClass: HashLocationStrategy}
    ],
  
  bootstrap: [AppComponent]
})
export class AppModule { }
