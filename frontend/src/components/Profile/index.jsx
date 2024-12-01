import React, { useEffect, useState , useContext} from 'react'
import classes from './style.module.css'
import { Navbar } from '../Navbar'
import api from '../../Services/api'
import { GlobalContext } from '../../Context'

export const Profile = () => {
  const {profile,setProfile} = useContext(GlobalContext);
  return (
    <div>
      <div>
        <div className='flex flex-row justify-center items-center'>
          <div className='flex flex-col justify-center items-center'>
            <img src={profile?.avtUrl} className='rounded-[50%] w-[200px] h-[200px] mt-[50px]'/>
            <h1 className='font-bold text-[24px] mt-[20px]'>{profile?.name}</h1>
            <p className='font-normal text-[16px] text-[#787486]'>{profile?.role.charAt(0).toUpperCase() + profile?.role.slice(1).toLowerCase()}</p>
          </div>
        </div>
        <div className='flex flex-row justify-center items-center mt-[50px]'>
          <div className='flex flex-col justify-center items-center'>
            <div className='flex flex-row justify-center items-center'>
              <div className='flex flex-col justify-center items-center'>
                <h1 className='font-bold text-[24px] mt-[20px]'>Information</h1>
                <div className='flex flex-row justify-between items-center w-[400px] mt-[20px]'>
                  <p className='font-normal text-[16px] text-[#787486]'>Full Name</p>
                  <p className='font-normal text-[16px] text-[#787486]'>{profile?.name}</p>
                </div>
                <div className='flex flex-row justify-between items-center w-[400px] mt-[20px]'>
                  <p className='font-normal text-[16px] text-[#787486]'>MSSV:</p>
                  <p className='font-normal text-[16px] text-[#787486]'>{profile?.mssv}</p>
                </div>
                <div className='flex flex-row justify-between items-center w-[400px] mt-[20px]'>
                  <p className='font-normal text-[16px] text-[#787486]'>Email:</p>
                  <p className='font-normal text-[16px] text-[#787486]'>{profile?.email}</p>
                </div>
                <div className='flex flex-row justify-between items-center w-[400px] mt-[20px]'>
                  <p className='font-normal text-[16px] text-[#787486]'>Phone</p>
                  <p className='font-normal text-[16px] text-[#787486]'>{profile?.phone}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
