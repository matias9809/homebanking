const { createApp } = Vue
createApp( {
    data(){
        return {
            post:{
                firstname:"",
                lastname:"",
                email:"",
            },
            client:[],
            account:[],
            cards:[],
            cards_credit:[],
            cards_debit:[],
            typeCard:"",
            colorCard:"",
            date:undefined,
            bool:false,
            cardDelete:""
        }
    },
    created(){
        this.load_data();  
        this.date=new Date().toISOString().slice(0,10)
        console.log(this.date)    
    },
    methods: {
        load_data(){
            axios.get(`/api/clients/current`)
            .then(response=>{
                this.client=response.data;
                if(this.client.account){
                    this.account=this.client.account
                }
                this.cards=response.data.cards
                if(response.data.cards.filter(card=>card.type=='DEBIT').length>0){
                    this.cards_debit=[...response.data.cards.filter(cards=>cards.type=='DEBIT')]
                }
                if(response.data.cards.filter(card=>card.type=='CREDIT').length>0){
                    this.cards_credit=[...response.data.cards.filter(cards=>cards.type=='CREDIT')]
                }
                console.log(this.cards_credit,this.cards_debit);
            })
            .catch(err=>console.log(err))
        },
        logout(){
            axios.post('/api/logout')
            .then(response => location.href = "./index.html")
            .catch(err=>console.log(err))
        },
        createAccount(){
                axios.post('/api/clients/current/accounts',{
                headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response =>{
                    alert("your account was created successful"),
                    this.load_data()
                    })
                .catch(err=>alert(err))
        },
        createCard(){
            axios.post('/api/clients/current/cards',`typeCard=${this.typeCard}&colorCard=${this.colorCard}`,{
                headers:{
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(()=>{
                alert("your card was created successful"),
                    window.location.href = '/web/cards.html';
                })
            .catch(err=>alert(err))
        },
        deleteCard(){
            axios.patch(`/api/delete/card?number=${this.cardDelete}`,{
                headers:{
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(()=>{
                    alert("your card was deleted successful"),
                    this.load_data();
                    window.location.href = '/web/cards.html';
                })
            .catch(err=>alert(err))
        }
    },

} ).mount("#app")