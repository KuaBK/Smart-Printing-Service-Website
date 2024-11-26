import React, { useRef } from 'react';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';
import logo from '../../assets/hcmut.svg';
import generatePDF from 'react-to-pdf';


export const BillToPDF = ({billData}) => {
  const printRef = useRef();
  
  const Bill = ({billData}) => {
    return (
      <div className={`w-[800px] p-[30px]`}>
        <div className='flex flex-row justify-around items-center my-[30px]'>
          <div className='flex flex-row w-[220px] justify-items-center items-center h-[100%] center justify-center'>
            <img src={logo} className='p-[5px] w-[50px] h-[60px] py-[10px]'/>
            <h1 className='font-semibold text-xl text-[#0D062D]'>HCMUT-SSPS</h1>
          </div>
          <h1 className='font-bold text-xl text-[#0D062D] h-fit'>Invoice</h1>
        </div>
        <h1 className='font-bold p-[5px] mt-[40px]'>Bill id: #{billData.id}</h1>
        <h2 className='flex flex-row px-[5px]'> <p className='font-bold pr-[5px]'>Customer:</p> {billData.studentName}</h2>
        <h2 className='flex flex-row px-[5px]'> <p className='font-bold pr-[5px]'>MSSV:</p> {billData.mssv}</h2>
        <h2 className='flex flex-row px-[5px]'> <p className='font-bold pr-[5px]'>Time:</p> {billData.paidTime}</h2>
        <h2 className='flex flex-row px-[5px]'> <p className='font-bold pr-[5px]'>Method:</p> {billData.method}</h2>
        {billData.discountCode != 'NaN' ? <h2 className='flex flex-row px-[5px]'> <p className='font-bold pr-[5px]'>Discount code:</p> {billData.discountCode}</h2> : null}
        <div className="w-full h-[100px] p-6 my-[50px] font-sans">
          <div className="flex justify-between mb-2 w-full">
            <div className="text-gray-700">Số lượng giấy mua</div>
            <div className="text-gray-700">{billData.numberOfPages} tờ</div>
          </div>
          <div className="flex justify-between mb-2 w-full">
            <div className="text-gray-700">Tổng tiền giấy</div>
            <div className="text-gray-700">{billData.numberOfPages * 500} vnd</div>
          </div>
          <div className="flex justify-between mb-2 w-full">
            <div className="text-gray-700">Giảm giá:</div>
            <div className="text-gray-700">{billData.amount - billData.numberOfPages * 500} vnd</div>
          </div>
          <div className="flex justify-between font-bold text-lg w-full">
            <div className="text-gray-900 font-bold">Tổng tiền thanh toán:</div>
            <div className="text-gray-900 font-bold">{billData.amount} vnd</div>
          </div>
        </div>
      </div>
    );
  };

  return (
    <div>
      <div ref={printRef}>
        <Bill
          billData={billData}
        />
      </div>
      <button onClick={() => generatePDF(printRef, {filename: billData.code})} className='mt-[50px] px-[10px] py-[10px] font-bold text-[#ffff] bg-[#0f6dbf] rounded-xl h-fit'>Save as PDF</button>
    </div>
  );
};

