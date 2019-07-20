import { NgModule }                         from '@angular/core';
import { CommonModule }                     from '@angular/common';
import { Routes, RouterModule }             from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule }                       from '@angular/http';
import { CsvFileComponent }                    from './CsvFile.component';
import { TestRoutes }                       from './CsvFile.routes';
import { FileUtil }                         from './CsvFile.util';
import { Constants }                        from './CsvFile.constants';
import { MatButtonModule,MatTableModule,MatFormFieldModule, MatInputModule,MatPaginatorModule, MatRippleModule } from '@angular/material';

@NgModule({
    //put all your modules here
    //The imports key in the context of an @NgModule defines additional modules 
    //that will be imported into the current module

    imports: [ 
        CommonModule,
        TestRoutes,
        FormsModule,
        RouterModule,
        ReactiveFormsModule,
        HttpModule,
        MatFormFieldModule,
        MatInputModule,
        MatTableModule,
        MatPaginatorModule
        
    ],

    // put all your components / directives / pipes here
    declarations:[
        CsvFileComponent
    ],

    // put all your services here
    providers: [
        FileUtil,
        Constants
    ],
})

export class CsvFileModule{}