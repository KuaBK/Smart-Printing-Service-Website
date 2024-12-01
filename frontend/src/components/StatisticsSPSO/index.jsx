
import React, { useState, useEffect } from 'react';
import classes from './style.module.css';
import api from '../../Services/api';
import { DataGrid } from '@mui/x-data-grid';

export const StatisticsSPSO = () => {
  const [reportData, setReportData] = useState(null);
  const nameUser = "Tran Loc ec dok";

  useEffect(() => {
    api.get('/spso/daily-report')
      .then(response => {
        if (response.data.status === 200) {
          setReportData(response.data.data);
        }
      })
      .catch(error => {
        console.error('There was an error fetching the report data!', error);
      });
  }, []);

  const columns = [
    { field: 'date', headerName: 'Date', width: 150 },
    { field: 'numOfPayments', headerName: 'Number of Payments', width: 120 },
    { field: 'amountPaid', headerName: 'Amount Paid', width: 120 },
    { field: 'numPagesBought', headerName: 'Number of Pages Bought', width: 120 },
    { field: 'totalPagesPrinted', headerName: 'Total Pages Printed', width: 120 },
    { field: 'totalPrints', headerName: 'Total Prints', width: 120 },
    { field: 'numAccess', headerName: 'Number of Access', width: 120 },
  ];

  const rows = reportData?.report.map((report, index) => ({
    id: index,
    date: report.date,
    numOfPayments: report.numOfPayments,
    amountPaid: report.amountPaid,
    numPagesBought: report.numPagesBought,
    totalPagesPrinted: report.totalPagesPrinted,
    totalPrints: report.totalPrints,
    numAccess: report.numAccess,
  }));

  return (
    <div className={classes.container}>
      <div className={classes.info}>
        <div className={classes.info_h}>Hi, {nameUser}</div>
        <p className={classes.info_p}>Hope you have a great day</p>
      </div>
      <div className={classes.body}>
        <div className={classes.header_}>
          <div className={classes.statistics}>Statistics</div>
          <div className={classes.sandf}>
            <select className={classes.filter}>
              <option value="All">Filter</option>
            </select>
            <input className={classes.search} placeholder='Search for Library...' />
          </div>
        </div>
        <div className={classes.body_view}>
          <div className={classes.statis_nav}>
            <div className={classes.nav_countAccess}>Daily Report</div>
            <button className={classes.btn_download}><a href={reportData?.['url-download']}>Download</a></button>
          </div>
          <div className={classes.viewReport}>
            <div className="m-[5px] bg-white h-[500px]">
              <DataGrid rows={rows} columns={columns} pageSize={5} disableColumnReorder />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};


