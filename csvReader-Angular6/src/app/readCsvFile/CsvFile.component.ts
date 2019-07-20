import { Component, OnInit, ViewChild } from "@angular/core";
import { Router }                       from "@angular/router";
import { FileUtil }                     from './CsvFile.util';
import { Constants }                    from './CsvFile.constants';
import { Issue }                        from './Issue';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';

@Component({

  selector:'app-root',
  styleUrls: ['./CsvFile.component.css'],
  templateUrl: './CsvFile.component.html'
})

export class CsvFileComponent implements OnInit {

  @ViewChild('fileImportInput')
  fileImportInput: any;
  issueList: Issue[] = new Array();
  csvRecords = [];
  displayedColumns = ['First Name', 'Sur Name', 'Issue Count', 'Date of Birth'];
  dataSource: MatTableDataSource<Issue>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private _router: Router,
    private _fileUtil: FileUtil
  ) { 

   this.dataSource = new MatTableDataSource([]);

  }

  ngOnInit() {
   }

  // METHOD CALLED WHEN CSV FILE IS IMPORTED
  fileChangeListener($event): void {
    this.issueList=[];
    var text = [];
    var target = $event.target || $event.srcElement;
    var files = target.files; 

    if(Constants.validateHeaderAndRecordLengthFlag){
      if(!this._fileUtil.isCSVFile(files[0])){
        alert("Please import valid .csv file.");
        this.fileReset();
      }
    }

    var input = $event.target;
    var reader = new FileReader();
    reader.readAsText(input.files[0]);

    reader.onload = (data) => {
      let csvData = reader.result;
      let csvRecordsArray = csvData.split(/\r\n|\n/);

      var headerLength = -1;
      if(Constants.isHeaderPresentFlag){
        let headersRow = this._fileUtil.getHeaderArray(csvRecordsArray, Constants.tokenDelimeter);
        headerLength = headersRow.length; 
      }
      
       this.csvRecords = this._fileUtil.getDataRecordsArrayFromCSVFile(csvRecordsArray, 
          headerLength, Constants.validateHeaderAndRecordLengthFlag, Constants.tokenDelimeter);

        /* Remove the first header row */
        this.csvRecords.splice(0, 1);

        this.csvRecords.map((record: any) => {
             this.issueList.push({
              firstName: record[0],
              surName: record[1],
              issueCount: record[2],
              dateOfBirth: record[3],
          })
      });

      this.issueList.splice(this.issueList.length-1, 1);
      this.dataSource = new MatTableDataSource(this.issueList);


      if(this.csvRecords == null){
        //If control reached here it means csv file contains error, reset file.
        this.fileReset();
      }    
    }

    reader.onerror = function () {
      alert('Unable to read ' + input.files[0]);
    };


  };

  fileReset(){
    this.fileImportInput.nativeElement.value = "";
    this.csvRecords = [];
    this.dataSource = new MatTableDataSource([]);
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

}