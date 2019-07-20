import { Routes, RouterModule } from '@angular/router';

import { CsvFileComponent } from './readCsvFile/CsvFile.component';

export const appRoutes: Routes = [
    { 
        path: 'test', 
        component: CsvFileComponent,
    },
    { 
        path: '', 
        component: CsvFileComponent, 
    }
];
