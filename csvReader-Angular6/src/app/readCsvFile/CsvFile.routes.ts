import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { CsvFileComponent } from './CsvFile.component';

const routes: Routes = [
    { 
        path: '',  
        component: CsvFileComponent 
    }
];
export const TestRoutes: ModuleWithProviders = RouterModule.forChild(routes);