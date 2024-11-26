import React, {useState, useContext, useEffect} from 'react'
import classes from './style.module.css'
import { GlobalContext } from '../../Context'
import {BillToPDF} from '../BillToPDF'
import api from '../../Services/api'

export const HistoryTransaction = () => {
    const { bill, setBill } = useContext(GlobalContext);
    const [activate, setActivate] = useState(false);
    const [selectbill, setSelectBill] = useState(bill[0]?.title);
    const [billData, setBillData] = useState();
    useEffect(() => {
      const fetchData = async () => {
        try {
          const res = await api.get('/payment');
          setBill(res.data);
        } catch (error) {
          console.error('Error fetching bills:', error);
        }
      };

      fetchData();
      if (bill){
        setSelectBill(bill[0]);
      }
    }, []);
    return (
      <div className={`flex flex-row justify-around mt-[20px]`}>
        <ul className={`h-full m-[20px] w-[250px] cursor-pointer` }>
          {bill.map((billItem, index) => (
            <li key={index} className={selectbill?.id != billItem?.id ? `flex flex-row my-[5px]` : ` my-[5px] flex flex-row rounded-lg bg-[#5030e514]`} onClick={() => setSelectBill(billItem)}>
              <div className={`py-[10px] px-[10px]`}>
                <h2 className='font-bold cursor-pointer'>#{billItem?.id}</h2>
                <p className='text-[12px] text-gray-500'>{billItem?.paidTime}</p>
              </div>
            </li>
          ))}
        </ul>
        <div className={`h-full m-[20px] w-[820px]`}>
            <div className='flex flex-row'>
              {selectbill && <BillToPDF billData={selectbill}/>}
            </div>
        </div>
      </div>
    );
}
