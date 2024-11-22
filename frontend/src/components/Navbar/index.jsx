import React, {useState, useContext} from 'react'
import classes from './style.module.css'
import logo from '../../assets/hcmut.svg'
import calender from '../../assets/calender.svg'
import messagequestion from '../../assets/messagequestion.svg'
import notifications from '../../assets/notifications.svg'
import test from '../../assets/test.svg'
import arrowdown from '../../assets/arrowdown.svg'
import filterimage from '../../assets/filterimage.svg'
import { GlobalContext } from '../../Context'
import {useNavigate} from 'react-router-dom'

export const Navbar = () => {
  const { noti, setNoti } = useContext(GlobalContext);
  const {selecInput, setSelecInput} = useContext(GlobalContext)
  const [showProfile, setShowProfile] = useState(false)
  const [showNotification, setShowNotification] = useState(false)
  const {profile} = useContext(GlobalContext);
  const navigate = useNavigate();
  const logout = () => {
    localStorage.removeItem('JWT_TOKEN')
    // localStorage.setItem('ROLE', 'user')
    localStorage.removeItem('USER')
    localStorage.removeItem('CSRF_TOKEN')
    navigate('/login')
  }
  return (
    <div className='relative'>
      <div className={classes.header_border}></div>
      <div className="h-[70px] flex flex-row justify-between after:content-[''] after:bottom-0 after:left-0 after:w-screen after:h-[1px] after:bg-[#DBDBDB] after:absolute">
        <div className='flex flex-row w-[220px] justify-items-center items-center h-[100%] center justify-center ' onClick={() => setSelecInput('Homepage')}>
          <img src={logo} className='p-[5px] w-[50px] h-[50px] py-[10px]'/>
          <h1 className='text-xl text-[#303030] font-[650]'>HCMUT-SSPS</h1>
        </div>
        <div className='min-w-[300px] flex flex-row'>
          <ul className='flex flex-row justify-items-center justify-between items-center h-[100%] cursor-pointer min-w-[120px]'>
            <li className='relative'>
              <img onClick={() => setShowNotification(!showNotification)} src={notifications} className='p-[5px] leading-[60px]'/>
              {showNotification && <div className={`absolute top-[60px] right-0 bg-[#FFFFFF] border-[1px] border-[#DBDBDB] rounded-[10px] z-50`}>
              <ul className='flex flex-col min-w-[300px] '>
                {noti.slice(0, 10).map((notification, index) => (
                  <li key={index} className='flex flex-row w-full hover:bg-[#0f6cbf] hover:text-[#ffff] p-[10px]  px-[15px]'>
                    <img src={notification.avatar} className='p-[5px] h-[40px] w-[40px] mr-[10px]'/>
                    <div className=''>
                      <h4>{notification.title}</h4>
                      <p className=' text-[12px]'>{notification.time}</p>
                    </div>
                  </li>
                ))}
                <div onClick={() => {setSelecInput('notification'); setShowNotification(false);}} className="text-center py-[10px] after:content-[''] after:top-0 after:left-0 after:w-full after:h-[1px] after:bg-[#DBDBDB] after:absolute relative">Xem tất cả</div>
              </ul>
            </div>}
            </li>
            <li><img src={calender} className='p-[5px] leading-[60px]'/></li>
            <li><img src={messagequestion} className='p-[5px] leading-[60px]'/></li>
          </ul>
          <div className='flex flex-row justify-items-center items-center h-[100%] cursor-pointer mr-5'>
            <div className='min-w-[150px] items-end flex flex-col px-[10px]'>
              <p className='font-bold text-[16px] leading-[16px]'>{profile?.name}</p>
              <p className='font-normal text-[14px] leading-[16px] text-[#787486] mt-[5px]'>{profile?.role.charAt(0).toUpperCase() + profile?.role.slice(1).toLowerCase()}</p>
            </div>
            <div className='flex flex-row justify-items-center items-center h-[100%] w-[60px] justify-between relative' onClick={() => setShowProfile(!showProfile)}>
              <img src={test} className='rounded-[50%] w-[38px] h-[38px]'/>
              <img src ={arrowdown} className={showProfile ? `w-[20px] h-[20px] p-[5px] rotate-180`: 'w-[20px] h-[20px] p-[5px]' }/>
              {showProfile && <div className={`absolute top-[80px] right-0 bg-[#FFFFFF] border-[1px] border-[#DBDBDB] rounded-[10px] z-50`}>
                <ul className='flex flex-col min-w-[230px] py-[10px]'>
                  <li className='p-[5px] hover:bg-[#0f6cbf] hover:text-[#ffff] px-[15px]' onClick={() => setSelecInput('Profile')}>Tài khoản</li>
                  <li className='p-[5px] hover:bg-[#0f6cbf] hover:text-[#ffff] px-[15px]'>Cài đặt</li>
                  <li className='p-[5px] hover:bg-[#0f6cbf] hover:text-[#ffff] px-[15px]' onClick={() => setSelecInput('BuyPrintingPages')}>Mua trang in</li>
                  <li className='p-[5px] hover:bg-[#0f6cbf] hover:text-[#ffff] px-[15px]' onClick={() => setSelecInput('HistoryTransaction')}>Lịch sử giao dịch</li>
                  <li className='p-[5px] hover:bg-[#0f6cbf] hover:text-[#ffff] px-[15px]' onClick={logout}>Đăng xuất</li>
                </ul>
              </div>}
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}


